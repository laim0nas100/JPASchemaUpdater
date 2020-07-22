package lt.lb.jpaschemaupdater.ported.misc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateStategy.ConnectionSchemaUpdateStrategy;
import lt.lb.jpaschemaupdater.ported.misc.Scripting.ScriptReadOptions;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author laim0nas100
 */
public class ResourceReadingUtils {

    public static List<ConnectionSchemaUpdateStrategy> fromResourceScript(
            URL resourceScript,
            boolean continueOnError,
            boolean ignoreFailedDrops, ScriptReadOptions opt) throws IOException, URISyntaxException {
        
        String scripts = IOUtils.toString(resourceScript, StandardCharsets.UTF_8);

        LinkedList<String> statements = new LinkedList<>();
        Scripting.splitSqlScript(scripts, opt, statements);

        return statements.stream().map(s -> {
            return (ConnectionSchemaUpdateStrategy) (Connection t) -> {
                Scripting.executeSqlScript(t, s, continueOnError, ignoreFailedDrops, opt);
            };
        }).collect(Collectors.toList());

    }

    public static Optional<String> getFilePath(URL resource) throws URISyntaxException {
        return Optional.of(FilenameUtils.getFullPathNoEndSeparator(resource.getFile()));
    }

}
