package eu.amidst.core.database.statics.readers.impl;

import com.google.common.collect.ImmutableSet;
import eu.amidst.core.database.Attribute;
import eu.amidst.core.database.Attributes;
import eu.amidst.core.header.StateSpaceType;

import java.util.Set;

/**
 * Created by sigveh on 10/16/14.
 */
public class ForTesting4Attributes extends Attributes {

    private final Attribute CLASS = new Attribute(0, "CLASS", "NA", StateSpaceType.MULTINOMIAL);
    private final Attribute TWO_NAMES = new Attribute(1, "TWO NAMES", "NA", StateSpaceType.REAL);
    private final Attribute THREE_NAMES_HERE = new Attribute(1, "THREE NAMES HERE", "NA", StateSpaceType.REAL);

    private static Set<Attribute> attributesTesting4;
    {
        attributesTesting4 = ImmutableSet.of(CLASS, TWO_NAMES, THREE_NAMES_HERE);
    }

    public ForTesting4Attributes(){
        super(attributesTesting4);
    }

    @Override
    public Set<Attribute> getSet(){
        return attributesTesting4;
    }

    @Override
    public void print() {

    }

    @Override
    public Attribute getAttributeByName(String name) {
        return null;
    }
}
