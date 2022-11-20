package lt.lb.jpaschemaupdater.misc;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lt.lb.jpaschemaupdater.JPASchemaUpdateStategy.ConnectionSchemaUpdateStrategy;
import lt.lb.jpaschemaupdater.misc.Scripting.ScriptReadOptions;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author laim0nas100
 */
public class ResourceReadingUtils {
    
    public static Long getVersionFromFileName(URL url) {
        return ResourceReadingUtils.getFilePath(url)
                .map(FilenameUtils::removeExtension)
                .map(FilenameUtils::getName)
                .map(StringUtils::getDigits)
                .map(Long::parseLong)
                .get();
    }
    
    public static List<ConnectionSchemaUpdateStrategy> fromResourceScript(
            URL resourceScript,
            boolean continueOnError,
            boolean ignoreFailedDrops, ScriptReadOptions opt) throws IOException {
        
        String scripts = IOUtils.toString(resourceScript, opt.charset);
        
        LinkedList<String> statements = new LinkedList<>();
        Scripting.splitSqlScript(scripts, opt, statements);
        
        return statements.stream().map(s -> {
            return (ConnectionSchemaUpdateStrategy) (Connection t) -> {
                Scripting.executeSqlScript(t, s, continueOnError, ignoreFailedDrops, opt);
            };
        }).collect(Collectors.toList());
        
    }
    
    public static Optional<String> getFilePath(URL resource) {
        return Optional.ofNullable(resource).map(m -> m.getFile());
    }
    
}
