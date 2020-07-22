package lt.lb.jpaschemaupdater.ported.misc;

import com.sun.tools.doclets.standard.Standard;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import static lt.lb.jpaschemaupdater.ported.misc.SimpleAssert.notBlank;
import static lt.lb.jpaschemaupdater.ported.misc.SimpleAssert.notEmpty;
import static lt.lb.jpaschemaupdater.ported.misc.SimpleAssert.notNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * Copied relevant functions from Spring's ScriptUtils
 *
 * @author laim0nas100
 */
public class Scripting {

    private static final Log logger = LogFactory.getLog(Scripting.class);

    /**
     * Does the provided SQL script contain the specified delimiter?
     *
     * @param script the SQL script
     * @param delim String delimiting each statement - typically a ';' character
     * @return
     */
    public static boolean containsSqlScriptDelimiters(String script, String delim) {
        boolean inLiteral = false;
        for (int i = 0; i < script.length(); i++) {
            if (script.charAt(i) == '\'') {
                inLiteral = !inLiteral;
            }
            if (!inLiteral && script.startsWith(delim, i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Split an SQL script into separate statements delimited by the provided
     * separator string.Each individual statement will be added to the provided
     * {@code List}
     * .<p>
     * Within the script, the provided {@code commentPrefix} will be honored:
     * any text beginning with the comment prefix and extending to the end of
     * the line will be omitted from the output. Similarly, the provided
     * {@code blockCommentStartDelimiter} and {@code blockCommentEndDelimiter}
     * delimiters will be honored: any text enclosed in a block comment will be
     * omitted from the output. In addition, multiple adjacent whitespace
     * characters will be collapsed into a single space.
     *
     * @param script the SQL script; never {@code null} or empty
     * @param statements the list that will contain the individual statements
     * @throws ScriptEx if an error occurred while splitting the SQL script
     */
    public static void splitSqlScript(String script, ScriptReadOptions opt, List<String> statements)
            throws ScriptEx {

        notBlank(script);

        StringBuilder sb = new StringBuilder();
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        boolean inEscape = false;
        for (int i = 0; i < script.length(); i++) {
            char c = script.charAt(i);
            if (inEscape) {
                inEscape = false;
                sb.append(c);
                continue;
            }
            // MySQL style escapes
            if (c == '\\') {
                inEscape = true;
                sb.append(c);
                continue;
            }
            if (!inDoubleQuote && (c == '\'')) {
                inSingleQuote = !inSingleQuote;
            } else if (!inSingleQuote && (c == '"')) {
                inDoubleQuote = !inDoubleQuote;
            }
            if (!inSingleQuote && !inDoubleQuote) {
                if (script.startsWith(opt.separator, i)) {
                    // We've reached the end of the current statement
                    if (sb.length() > 0) {
                        statements.add(sb.toString());
                        sb = new StringBuilder();
                    }
                    i += opt.separator.length() - 1;
                    continue;
                } else if (script.startsWith(opt.commentPrefix, i)) {
                    // Skip over any content from the start of the comment to the EOL
                    int indexOfNextNewline = script.indexOf("\n", i);
                    if (indexOfNextNewline > i) {
                        i = indexOfNextNewline;
                        continue;
                    } else {
                        // If there's no EOL, we must be at the end of the script, so stop here.
                        break;
                    }
                } else if (script.startsWith(opt.blockCommentStartDelimiter, i)) {
                    // Skip over any block comments
                    int indexOfCommentEnd = script.indexOf(opt.blockCommentEndDelimiter, i);
                    if (indexOfCommentEnd > i) {
                        i = indexOfCommentEnd + opt.blockCommentEndDelimiter.length() - 1;
                        continue;
                    } else {
                        throw new ScriptEx(
                                "Missing block comment end delimiter: " + opt.blockCommentEndDelimiter);
                    }
                } else if (c == ' ' || c == '\n' || c == '\t') {
                    // Avoid multiple adjacent whitespace characters
                    if (sb.length() > 0 && sb.charAt(sb.length() - 1) != ' ') {
                        c = ' ';
                    } else {
                        continue;
                    }
                }
            }
            sb.append(c);
        }
        if (StringUtils.isNotBlank(sb)) {
            statements.add(sb.toString());
        }
    }

    public static class ScriptReadOptions {

        public static final ScriptReadOptions DEFAULT = new ScriptReadOptions(StandardCharsets.UTF_8, "--", ";", "/*", "*/", "^^^ END OF SCRIPT ^^^", "\n");

        /**
         * Comment prefix, generally "--" in sql scripts
         */
        public final String commentPrefix;

        /**
         * Script separator, that indicates separate command
         */
        public final String separator;
        /**
         * Block comment start delimiter cannot be empty or white space
         */
        public final String blockCommentStartDelimiter;

        /**
         * Block comment start delimiter cannot be empty or white space
         */
        public final String blockCommentEndDelimiter;

        public final String eofStatementSeparator;
        public final String fallbackStatementSeparator;

        public final Charset charset;

        ScriptReadOptions check(String script) {
            if (!eofStatementSeparator.equals(separator) && !containsSqlScriptDelimiters(script, separator)) {
                return this.withSeparator(fallbackStatementSeparator);
            }
            return this;
        }

        public ScriptReadOptions(Charset charset, String commentPrefix, String separator, String blockCommentStartDelimiter, String blockCommentEndDelimiter, String eofStatementSeparator, String fallbackStatementSeparator) {
            this.charset = notNull(charset);
            this.commentPrefix = notBlank(commentPrefix);
            this.separator = notEmpty(separator);
            this.blockCommentStartDelimiter = notBlank(blockCommentStartDelimiter);
            this.blockCommentEndDelimiter = notBlank(blockCommentEndDelimiter);
            this.eofStatementSeparator = notBlank(eofStatementSeparator);
            this.fallbackStatementSeparator = notEmpty(fallbackStatementSeparator);

        }

        public ScriptReadOptions withSeparator(String sep) {
            return new ScriptReadOptions(charset, commentPrefix, sep, blockCommentStartDelimiter, blockCommentEndDelimiter, eofStatementSeparator, fallbackStatementSeparator);
        }

        public ScriptReadOptions withCommentPrefix(String prefix) {
            return new ScriptReadOptions(charset, prefix, separator, blockCommentStartDelimiter, blockCommentEndDelimiter, eofStatementSeparator, fallbackStatementSeparator);
        }

        public ScriptReadOptions withBlockComment(String start, String end) {
            return new ScriptReadOptions(charset, commentPrefix, separator, start, end, eofStatementSeparator, fallbackStatementSeparator);
        }

        public ScriptReadOptions withEofAndFallback(String eof, String fallback) {
            return new ScriptReadOptions(charset, commentPrefix, separator, blockCommentStartDelimiter, blockCommentEndDelimiter, eof, fallback);
        }

        public ScriptReadOptions withCharset(Charset charset) {
            return new ScriptReadOptions(charset, commentPrefix, separator, blockCommentStartDelimiter, blockCommentEndDelimiter, eofStatementSeparator, fallbackStatementSeparator);
        }

        public ScriptReadOptions getDefault() {
            return DEFAULT;
        }

    }

    public static String buildErrorMessage(String stmt, int stmtNumber) {
        return String.format("Failed to execute SQL script statement #%s of %s", stmtNumber, stmt);
    }

    /**
     * Execute the given SQL script.
     * <p>
     * Statement separators and comments will be removed before executing
     * individual statements within the supplied script.
     * <p>
     * <strong>Warning</strong>: this method does <em>not</em> release the
     * provided {@link Connection}.
     *
     * @param connection the JDBC connection to use to execute the script;
     * already configured and ready to use
     * @param script
     * @param continueOnError whether or not to continue without throwing an
     * exception in the event of an error
     * @param ignoreFailedDrops whether or not to continue in the event of
     * specifically an error on a {@code DROP} statement
     * @param opt
     * @throws ScriptEx if an error occurred while executing the SQL script
     */
    public static void executeSqlScript(Connection connection, String script, boolean continueOnError,
            boolean ignoreFailedDrops, ScriptReadOptions opt) throws ScriptEx {

        notBlank(script);
        try {
            long startTime = System.currentTimeMillis();

            opt = opt.check(script);
            List<String> statements = new LinkedList<>();
            splitSqlScript(script, opt, statements);

            int stmtNumber = 0;
            Statement stmt = connection.createStatement();
            try {
                for (String statement : statements) {
                    stmtNumber++;
                    try {
                        stmt.execute(statement);
                        int rowsAffected = stmt.getUpdateCount();
                        if (logger.isDebugEnabled()) {
                            logger.debug(rowsAffected + " returned as update count for SQL: " + statement);
                            SQLWarning warningToLog = stmt.getWarnings();
                            while (warningToLog != null) {
                                logger.debug("SQLWarning ignored: SQL state '" + warningToLog.getSQLState()
                                        + "', error code '" + warningToLog.getErrorCode()
                                        + "', message [" + warningToLog.getMessage() + "]");
                                warningToLog = warningToLog.getNextWarning();
                            }
                        }
                    } catch (SQLException ex) {
                        boolean dropStatement = StringUtils.startsWithIgnoreCase(statement.trim(), "drop");
                        if (continueOnError || (dropStatement && ignoreFailedDrops)) {
                            if (logger.isDebugEnabled()) {
                                logger.debug(buildErrorMessage(statement, stmtNumber), ex);
                            }
                        } else {
                            throw new ScriptEx(buildErrorMessage(statement, stmtNumber), ex);
                        }
                    }
                }
            } finally {
                try {
                    stmt.close();
                } catch (Throwable ex) {
                    logger.debug("Could not close JDBC Statement", ex);
                }
            }

            long elapsedTime = System.currentTimeMillis() - startTime;
            if (logger.isInfoEnabled()) {
                logger.info("Executed SQL script in " + elapsedTime + " ms.");
            }
        } catch (Exception ex) {
            if (ex instanceof ScriptEx) {
                throw (ScriptEx) ex;
            }
            throw new ScriptEx(
                    "Failed to execute database script", ex);
        }
    }
}
