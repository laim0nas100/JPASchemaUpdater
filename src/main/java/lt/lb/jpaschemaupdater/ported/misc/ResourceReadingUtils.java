package lt.lb.jpaschemaupdater.ported.misc;

import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateStategy.ConnectionSchemaUpdateStrategy;
import lt.lb.jpaschemaupdater.ported.misc.Scripting.ScriptReadOptions;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author laim0nas100
 */
public class ResourceReadingUtils {

    public static List<ConnectionSchemaUpdateStrategy> fromResourceScript(
            URL resourceScript,
            boolean continueOnError,
            boolean ignoreFailedDrops, ScriptReadOptions opt) throws IOException, URISyntaxException {
        String scripts = Resources.toString(resourceScript, StandardCharsets.UTF_8);

        LinkedList<String> statements = new LinkedList<>();
        Scripting.splitSqlScript(scripts, opt, statements);

        return statements.stream().map(s -> {
            return (ConnectionSchemaUpdateStrategy) (Connection t) -> {
                Scripting.executeSqlScript(t, s, continueOnError, ignoreFailedDrops, opt);
            };
        }).collect(Collectors.toList());

    }

    public static Long getVersionFromFileName(URL resource) throws URISyntaxException {
        String path = getFileNameNoExt(resource);
        String digits = StringUtils.getDigits(path);
        return Long.parseLong(digits);
    }

    public static String getFileNameNoExt(URL resource) throws URISyntaxException {
        String name = Paths.get(resource.toURI()).getFileName().toString();
        return FilenameUtils.removeExtension(name);
    }
}