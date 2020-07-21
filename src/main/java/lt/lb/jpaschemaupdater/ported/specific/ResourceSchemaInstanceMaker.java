package lt.lb.jpaschemaupdater.ported.specific;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lt.lb.jpaschemaupdater.ported.JPASchemaInstanceMaker;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateInstance;
import lt.lb.jpaschemaupdater.ported.ManagedAccessFactory;
import lt.lb.jpaschemaupdater.ported.misc.JPASchemaUpdateException;
import lt.lb.jpaschemaupdater.ported.misc.ResourceReadingUtils;
import lt.lb.jpaschemaupdater.ported.misc.Scripting.ScriptReadOptions;

/**
 *
 * @author Laimonas-Beniusis-PC
 */
public class ResourceSchemaInstanceMaker<T extends ResourceSchemaInstanceMaker> implements JPASchemaInstanceMaker {

    protected List<URL> resourceFiles = new ArrayList<>();
    protected List<JPASchemaUpdateInstance> instances;
    protected ScriptReadOptions defaultOptions;
    protected ManagedAccessFactory managedAccessFactory;
    protected boolean continueOnError = false;
    protected boolean ignoreFailedDrops = false;
    protected List<ScriptOptionsByFilePattern> regexPatterns = new ArrayList<>();

    public static class ScriptOptionsByFilePattern {

        protected ScriptReadOptions scriptReadOptions;
        protected String pattern;
        private Pattern compiled;

        public ScriptOptionsByFilePattern() {
        }

        public ScriptOptionsByFilePattern(ScriptReadOptions scriptReadOptions, String pattern) {
            this.scriptReadOptions = scriptReadOptions;
            this.pattern = pattern;
        }

        public ScriptReadOptions getScriptReadOptions() {
            return scriptReadOptions;
        }

        public void setScriptReadOptions(ScriptReadOptions scriptReadOptions) {
            this.scriptReadOptions = scriptReadOptions;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public Pattern getCompiledPattern() {
            if (compiled == null) {
                compiled = Pattern.compile(pattern);
            }
            return compiled;
        }

    }

    public ResourceSchemaInstanceMaker() {

    }

    public ResourceSchemaInstanceMaker(ScriptReadOptions defaultOptions) {
        this.defaultOptions = defaultOptions;
    }

    public T addResource(URL res) {
        resourceFiles.add(res);
        return (T) this;
    }

    @Override
    public List<JPASchemaUpdateInstance> getInstances() {
        if (instances == null) {
            instances = getResourceFiles().stream().map(url -> generateInstance(url)).collect(Collectors.toList());
        }
        return instances;
    }

    protected JPASchemaUpdateInstance generateInstance(URL url) {
        try {
            String fileNameNoExt = ResourceReadingUtils.getFileNameNoExt(url);
            ScriptReadOptions options = getRegexPatterns().stream().filter(p -> {
                return p.getCompiledPattern().asPredicate().test(fileNameNoExt);
            }).map(m -> m.getScriptReadOptions()).findFirst().orElse(getDefaultOptions());
            ResourceSchemaUpdateInstance instance = new ResourceSchemaUpdateInstance(url, options, getManagedAccessFactory());
            instance.setContinueOnError(isContinueOnError());
            instance.setIgnoreFailedDrops(isIgnoreFailedDrops());
            
            return instance;
        } catch (URISyntaxException ex) {
            throw new JPASchemaUpdateException(ex);
        }
    }

    public void setInstances(List<JPASchemaUpdateInstance> instances) {
        this.instances = instances;
    }

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

    public List<ScriptOptionsByFilePattern> getRegexPatterns() {
        return regexPatterns;
    }

    public void setRegexPatterns(List<ScriptOptionsByFilePattern> regexPatterns) {
        this.regexPatterns = regexPatterns;
    }

}
