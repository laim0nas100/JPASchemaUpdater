package lt.lb.jpaschemaupdater.specific;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lt.lb.jpaschemaupdater.JPASchemaUpdateInstance;
import lt.lb.jpaschemaupdater.JPASchemaUpdateInstanceMaker;

/**
 *
 * @author laim0nas100
 */
public class CombinedSchemaUpdateInstanceMaker<Ver> implements JPASchemaUpdateInstanceMaker<Ver> {

    protected Collection<JPASchemaUpdateInstanceMaker<Ver>> makers;

    public CombinedSchemaUpdateInstanceMaker(JPASchemaUpdateInstanceMaker<Ver>... makers) {
        this(Arrays.asList(makers));
    }

    public CombinedSchemaUpdateInstanceMaker(Collection<JPASchemaUpdateInstanceMaker<Ver>> makers) {
        this.makers = makers;
    }

    @Override
    public List<JPASchemaUpdateInstance<Ver>> getInstances() {
        return makers.stream().flatMap(m -> m.getInstances().stream()).collect(Collectors.toList());
    }

}
