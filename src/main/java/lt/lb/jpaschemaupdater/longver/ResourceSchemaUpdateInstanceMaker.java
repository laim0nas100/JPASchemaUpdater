package lt.lb.jpaschemaupdater.longver;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lt.lb.jpaschemaupdater.JPASchemaUpdateInstance;
import lt.lb.jpaschemaupdater.JPASchemaUpdateInstanceMaker;
import lt.lb.jpaschemaupdater.ManagedAccessFactory;
import lt.lb.jpaschemaupdater.misc.JPASchemaUpdateException;
import lt.lb.jpaschemaupdater.misc.ResourceReadingUtils;
import lt.lb.jpaschemaupdater.misc.Scripting.ScriptReadOptions;
import lt.lb.jpaschemaupdater.specific.ResourceSchemaUpdateInstance;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author laimm0nas100
 * @param <M>
 * @param <Ver>
 */
public abstract class ResourceSchemaUpdateInstanceMaker<Ver, M extends ResourceSchemaUpdateInstanceMaker> implements JPASchemaUpdateInstanceMaker<Ver> {

    protected List<URL> resourceFiles = new ArrayList<>();
    protected List<JPASchemaUpdateInstance<Ver>> instances;
    protected ScriptReadOptions defaultOptions;
    protected ManagedAccessFactory managedAccessFactory;
    protected boolean continueOnError = false;
    protected boolean ignoreFailedDrops = false;
    protected List<ScriptOptionsByPattern> patterns = new ArrayList<>();

    public static class ScriptOptionsByPattern {

        protected ScriptReadOptions scriptReadOptions;
        protected Predicate<URL> pattern;

        public ScriptOptionsByPattern() {
        }

        public ScriptOptionsByPattern(ScriptReadOptions scriptReadOptions, Predicate<URL> pattern) {
            this.scriptReadOptions = scriptReadOptions;
            this.pattern = pattern;
        }

        public ScriptReadOptions getScriptReadOptions() {
            return scriptReadOptions;
        }

        public void setScriptReadOptions(ScriptReadOptions scriptReadOptions) {
            this.scriptReadOptions = scriptReadOptions;
        }

        public Predicate<URL> getPattern() {
            return pattern;
        }

        public void setPattern(Predicate<URL> pattern) {
            this.pattern = pattern;
        }

    }

    public ResourceSchemaUpdateInstanceMaker() {

    }

    public ResourceSchemaUpdateInstanceMaker(ScriptReadOptions defaultOptions, ManagedAccessFactory managedAccessFactory) {
        this.defaultOptions = defaultOptions;
        this.managedAccessFactory = managedAccessFactory;
    }

    protected abstract M me();

    public M addResource(URL res) {
        M me = me();
        me.resourceFiles.add(res);
        return me;
    }

    public M addPattern(ScriptOptionsByPattern pat) {
        M me = me();
        me.patterns.add(pat);
        return me;
    }

    public M addFilenamePattern(String str, ScriptReadOptions opt) {
        final Predicate<String> pattern = Pattern.compile(str).asPredicate();
        Predicate<URL> pred = (url) -> {
            try {
                return ResourceReadingUtils.getFilePath(url)
                        .map(FilenameUtils::getName)
                        .filter(pattern).isPresent();
            } catch (Exception ex) {
                throw new JPASchemaUpdateException(ex);
            }
        };
        M me = me();
        me.patterns.add(new ScriptOptionsByPattern(opt, pred));
        return me;
    }

    public M addFilePathPattern(String str, ScriptReadOptions opt) {
        final Predicate<String> pattern = Pattern.compile(str).asPredicate();
        Predicate<URL> pred = (url) -> {
            try {
                return ResourceReadingUtils.getFilePath(url)
                        .filter(pattern).isPresent();
            } catch (Exception ex) {
                throw new JPASchemaUpdateException(ex);
            }
        };

        M me = me();
        me.patterns.add(new ScriptOptionsByPattern(opt, pred));
        return me;
    }

    @Override
    public List<JPASchemaUpdateInstance<Ver>> getInstances() {
        if (instances == null) {
            instances = getResourceFiles().stream().map(url -> generateInstance(url)).collect(Collectors.toList());
        }
        return instances;
    }

    protected JPASchemaUpdateInstance<Ver> generateInstance(URL url) {
        ScriptReadOptions options = getPatterns().stream().filter(f -> f.getPattern().test(url))
                .map(m -> m.getScriptReadOptions()).findFirst().orElse(getDefaultOptions());

        ResourceSchemaUpdateInstance<Ver, ?> instance = createSchemaUpdateInstance(url, options, getManagedAccessFactory());
        instance.setContinueOnError(isContinueOnError());
        instance.setIgnoreFailedDrops(isIgnoreFailedDrops());
        return instance;
    }

    protected abstract ResourceSchemaUpdateInstance<Ver, ?> createSchemaUpdateInstance(URL url, ScriptReadOptions options, ManagedAccessFactory factory);

    public List<URL> getResourceFiles() {
        return resourceFiles;
    }

    public void setResourceFiles(List<URL> resourceFiles) {
        this.resourceFiles = resourceFiles;
    }

    public ScriptReadOptions getDefaultOptions() {
        return defaultOptions;
    }

    public void setDefaultOptions(ScriptReadOptions defaultOptions) {
        this.defaultOptions = defaultOptions;
    }

    public ManagedAccessFactory getManagedAccessFactory() {
        return managedAccessFactory;
    }

    public void setManagedAccessFactory(ManagedAccessFactory managedAccessFactory) {
        this.managedAccessFactory = managedAccessFactory;
    }

    public boolean isContinueOnError() {
        return continueOnError;
    }

    public void setContinueOnError(boolean continueOnError) {
        this.continueOnError = continueOnError;
    }

    public boolean isIgnoreFailedDrops() {
        return ignoreFailedDrops;
    }

    public void setIgnoreFailedDrops(boolean ignoreFailedDrops) {
        this.ignoreFailedDrops = ignoreFailedDrops;
    }

    public List<ScriptOptionsByPattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<ScriptOptionsByPattern> patterns) {
        this.patterns = patterns;
    }

}
