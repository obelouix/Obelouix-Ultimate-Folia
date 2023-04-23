package fr.obelouix.ultimate.I18N;

import fr.obelouix.ultimate.ObelouixUltimate;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;

import java.text.MessageFormat;
import java.util.*;

public class Translator {

    private static final GlobalTranslator globalTranslator = GlobalTranslator.translator();
    private static final TranslationRegistry REGISTRY = TranslationRegistry.create(Key.key("obelouix", "main"));
    private static final TranslatableComponentRenderer<Locale> RENDERER = TranslatableComponentRenderer.usingTranslationSource(REGISTRY);

    private static final Set<String> deathMessages = new HashSet<>();

    private static void registerAllTranslations(List<Locale> locales) {

        locales.forEach(
                locale -> {
                    final String language = locale.toLanguageTag().replace("-", "_");


                    final ResourceBundle resourceBundle = ResourceBundle.getBundle(
                            "translations." + language, locale, UTF8ResourceBundleControl.get());

                    REGISTRY.registerAll(locale, resourceBundle, true);

                    if (globalTranslator.addSource(REGISTRY)) {
                        ObelouixUltimate.getInstance().getComponentLogger().info(Component.text("Registered locale: " + language, NamedTextColor.GREEN));
                    }
                }
        );

        REGISTRY.defaultLocale(Locale.US);
    }

    public void init() {
        registerAllTranslations(List.of(Locale.US, Locale.FRANCE, Locale.CANADA_FRENCH));

        ResourceBundle.getBundle(
                        "translations.en_US", Locale.US, UTF8ResourceBundleControl.get())
                .keySet().stream().filter(key -> key.contains("obelouix.messages.death.")).forEach(deathMessages::add);

    }

    public Component translate(TranslatableComponent component, Locale locale) {
        return GlobalTranslator.render(component, locale);
    }

    public Component translate(Component component, Locale locale) {
        return GlobalTranslator.render(component, locale);
    }

    public MessageFormat translate(String key, Locale locale) {
        for (final net.kyori.adventure.translation.Translator translator : globalTranslator.sources()) {
            final MessageFormat format = translator.translate(key, locale);
            if (format != null) {
                return format;
            }
        }
        return null;
    }

    public TranslatableComponentRenderer<Locale> getRenderer() {
        return RENDERER;
    }

    public Set<String> getDeathMessages() {
        return deathMessages;
    }

}
