package com.adu21.step.function.demo.module;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.ProvisionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * Module to allow methods annotated with @{@link javax.annotation.PostConstruct} to be executed
 * after Guice context has been setup.
 */
public final class PostConstructModule extends AbstractModule {

    @Override
    protected void configure() {
        binder().bindListener(Matchers.any(), new PostConstructProvisionListener());
    }

    /**
     * Utility class to execute methods annotated with @PostConstruct on an instance.
     */
    private static class PostConstructProcessor {

        /**
         * Filter to get only the {@link Method}s annotated with {@link PostConstruct} annotation.
         */
        private final Predicate<Method> filterPostConstructMethods = m -> {
            PostConstruct annotation = m.getAnnotation(PostConstruct.class);
            return annotation != null;
        };

        /**
         * Invoke a method on an instance by consuming {@link Method}s.
         *
         * @param instance Instance of T on which to invoke the method.
         * @param <T>      Instance type.
         * @return {@link Consumer} of {@link Method}s which invokes the methods on behalf of the #instance.
         */
        private <T> Consumer<Method> invokeMethodOnInstance(final T instance) {
            return method -> {
                try {
                    method.invoke(instance);
                } catch (final Exception e) {
                    throw new RuntimeException(String.format("@PostConstruct error: %s", e.getMessage()), e);
                }
            };
        }

        /**
         * Invoke all methods annotated with @PostConstruct on given instance.
         *
         * @param instance The instance on which to execute @PostConstruct methods
         * @param <T>      Type of instance.
         */
        final <T> void invokePostConstructMethodsOn(final T instance) {
            Arrays.stream(instance.getClass().getMethods())
                .filter(filterPostConstructMethods)
                .forEach(invokeMethodOnInstance(instance));
        }
    }

    /**
     * {@link ProvisionListener} implementation which executes the @{@link PostConstruct} annotated
     * methods on instances created via a @{@link com.google.inject.Provides} annotation.
     */
    final static class PostConstructProvisionListener extends PostConstructProcessor implements ProvisionListener {
        @Override
        public <T> void onProvision(final ProvisionInvocation<T> provision) {
            invokePostConstructMethodsOn(provision.provision());
        }
    }

    /**
     * {@link TypeListener} implementation which executes the @{@link PostConstruct} annotated
     * methods on instances created by Guice (not via @Provides annotation).
     */
    final static class PostConstructTypeListener extends PostConstructProcessor implements TypeListener {
        @Override
        public <I> void hear(final TypeLiteral<I> type, final TypeEncounter<I> encounter) {
            // register listener on any injected type
            encounter.register((InjectionListener<I>)this::invokePostConstructMethodsOn);
        }
    }
}