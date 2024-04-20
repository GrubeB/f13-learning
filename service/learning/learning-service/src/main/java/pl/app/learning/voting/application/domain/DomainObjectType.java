package pl.app.learning.voting.application.domain;

public enum DomainObjectType {
    REFERENCE(DomainObjectType.REFERENCE_DISCRIMINATOR),
    TOPIC(DomainObjectType.TOPIC_DISCRIMINATOR),
    CATEGORY(DomainObjectType.CATEGORY_DISCRIMINATOR),
    COMMENT(DomainObjectType.COMMENT_DISCRIMINATOR),
    PATH(DomainObjectType.PATH_DISCRIMINATOR),
    GROUP(DomainObjectType.GROUP_DISCRIMINATOR);
    private final String discriminator;

    DomainObjectType(String discriminator) {
        this.discriminator = discriminator;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public static final String REFERENCE_DISCRIMINATOR = "R";
    public static final String TOPIC_DISCRIMINATOR = "T";
    public static final String CATEGORY_DISCRIMINATOR = "C";
    public static final String COMMENT_DISCRIMINATOR = "COM";
    public static final String GROUP_DISCRIMINATOR = "G";
    public static final String PATH_DISCRIMINATOR = "P";
}

