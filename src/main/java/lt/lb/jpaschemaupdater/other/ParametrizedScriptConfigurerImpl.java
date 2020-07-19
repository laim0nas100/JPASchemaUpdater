//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lt.lb.jpaschemaupdater.other;

import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.swizzle.stream.FixedTokenListReplacementInputStream;
import org.codehaus.swizzle.stream.MappedTokenHandler;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.Assert;

public class ParametrizedScriptConfigurerImpl implements PopulatorConfigurer {
    private static final String PARAM_PARAMETRIZED = "p";
    private Map<String, String> tokens = Maps.newHashMap();

    public ParametrizedScriptConfigurerImpl() {
    }

    @Override
    public void configure(ResourceDatabasePopulator populator, Resource resource) {
        Assert.notNull(populator, "Parameter 'populator' cannot be null.");
        Assert.notNull(resource, "Parameter 'resource' cannot be null.");
        String resourceName = FilenameUtils.removeExtension(resource.getFilename());
        if (resourceName.contains("p")) {
            populator.setScripts(new Resource[]{new ParametrizedScriptConfigurerImpl.WrappedResource(this.getTokens(), resource)});
        }

    }

    public Map<String, String> getTokens() {
        return this.tokens;
    }

    public void setTokens(Map<String, String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public int getOrder() {
        return 2147483647;
    }

    private static class WrappedResource implements Resource {
        private final Map<String, String> tokens = Maps.newHashMap();
        private final Resource resource;

        public WrappedResource(Map<String, String> tokens, Resource resource) {
            Iterator i$ = tokens.entrySet().iterator();

            while(i$.hasNext()) {
                Entry<String, String> entry = (Entry)i$.next();
                this.tokens.put("${" + (String)entry.getKey() + "}", entry.getValue());
            }

            this.resource = resource;
        }

        @Override
        public boolean exists() {
            return this.resource.exists();
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new FixedTokenListReplacementInputStream(
                    this.resource.getInputStream(), 
                    new ArrayList(this.getTokens().keySet()), 
                    new MappedTokenHandler(this.getTokens()));
        }

        @Override
        public boolean isReadable() {
            return this.resource.isReadable();
        }

        @Override
        public boolean isOpen() {
            return this.resource.isOpen();
        }

        @Override
        public URL getURL() throws IOException {
            return this.resource.getURL();
        }

        @Override
        public URI getURI() throws IOException {
            return this.resource.getURI();
        }

        @Override
        public File getFile() throws IOException {
            return this.resource.getFile();
        }

        @Override
        public long contentLength() throws IOException {
            return this.resource.contentLength();
        }

        @Override
        public long lastModified() throws IOException {
            return this.resource.lastModified();
        }

        @Override
        public Resource createRelative(String relativePath) throws IOException {
            return this.resource.createRelative(relativePath);
        }

        @Override
        public String getFilename() {
            return this.resource.getFilename();
        }

        @Override
        public String getDescription() {
            return this.resource.getDescription();
        }

        public Map<String, String> getTokens() {
            return this.tokens;
        }
    }
}
