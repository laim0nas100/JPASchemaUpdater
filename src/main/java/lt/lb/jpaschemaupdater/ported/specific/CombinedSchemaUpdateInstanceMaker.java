package lt.lb.jpaschemaupdater.ported.specific;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateInstance;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateInstanceMaker;

/**
 *
 * @author laim0nas100
 */
public class CombinedSchemaUpdateInstanceMaker implements JPASchemaUpdateInstanceMaker {

    protected Collection<JPASchemaUpdateInstanceMaker> makers;

    public CombinedSchemaUpdateInstanceMaker(JPASchemaUpdateInstanceMaker... makers) {
        this(Arrays.asList(makers));
    }

    public CombinedSchemaUpdateInstanceMaker(Collection<JPASchemaUpdateInstanceMaker> makers) {
        this.makers = makers;
    }

    @Override
    public List<JPASchemaUpdateInstance> getInstances() {
        return makers.stream().flatMap(m -> m.getInstances().stream()).collect(Collectors.toList());
    }

}
