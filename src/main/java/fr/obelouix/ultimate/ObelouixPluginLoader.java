package fr.obelouix.ultimate;

import com.google.common.collect.ImmutableList;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

public class ObelouixPluginLoader implements PluginLoader {
    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        //classpathBuilder.addLibrary(new JarLibrary(Path.of("dependency.jar")));

        MavenLibraryResolver resolver = new MavenLibraryResolver();
        ImmutableList.of(
                new DefaultArtifact("org.spongepowered:configurate-hocon:4.1.2"),
                new DefaultArtifact("cloud.commandframework:cloud-paper:1.8.1"),
                new DefaultArtifact("cloud.commandframework:cloud-minecraft-extras:1.8.1")
        ).forEach(defaultArtifact -> resolver.addDependency(new Dependency(defaultArtifact, null)));

        resolver.addRepository(new RemoteRepository.Builder("paper", "default", "https://repo.papermc.io/repository/maven-public/").build());

        classpathBuilder.addLibrary(resolver);
    }
}