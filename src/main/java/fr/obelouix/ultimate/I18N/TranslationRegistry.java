package fr.obelouix.ultimate.I18N;

public enum TranslationRegistry {
    COMMAND_COORDS_ENABLED("obelouix.command.coords.enabled"),
    COMMAND_COORDS_DISABLED("obelouix.command.coords.disabled"),
    COMMAND_COORDS_ALREADY_ENABLED("obelouix.command.coords.already.enabled"),
    COMMAND_COORDS_ALREADY_DISABLED("obelouix.command.coords.already.disabled"),
    DIRECTION("obelouix.direction"),
    DIRECTION_NORTH("obelouix.direction.north"),
    DIRECTION_NORTH_EAST("obelouix.direction.northeast"),
    DIRECTION_NORTH_WEST("obelouix.direction.northwest"),
    DIRECTION_SOUTH("obelouix.direction.south"),
    DIRECTION_SOUTH_EAST("obelouix.direction.southeast"),
    DIRECTION_SOUTH_WEST("obelouix.direction.southwest"),
    DIRECTION_EAST("obelouix.direction.east"),
    DIRECTION_WEST("obelouix.direction.west");

    private final String translationKey;

    TranslationRegistry(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getTranslationKey() {
        return translationKey;
    }

}
