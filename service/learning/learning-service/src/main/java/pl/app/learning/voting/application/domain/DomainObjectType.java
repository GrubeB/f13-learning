package pl.app.learning.voting.application.domain;

public enum DomainObjectType {
    REFERENCE(DomainObjectType.REFERENCE_DISCRIMINATOR),
    TOPIC(DomainObjectType.TOPIC_DISCRIMINATOR),
    CATEGORY(DomainObjectType.CATEGORY_DISCRIMINATOR),
    COMMENT(DomainObjectType.COMMENT_DISCRIMINATOR);
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
}

