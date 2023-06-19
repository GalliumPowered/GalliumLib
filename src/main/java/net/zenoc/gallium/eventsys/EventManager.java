package net.zenoc.gallium.eventsys;

import com.google.common.collect.ArrayListMultimap;
import net.zenoc.gallium.api.annotations.EventListener;
import net.zenoc.gallium.api.event.Event;

import java.util.Arrays;

public class EventManager {
    public ArrayListMultimap<Class<? extends Event>, MListener> listeners = ArrayListMultimap.create();

    public EventManager() {

    }

    public void registerEvent(Object listener) {
        Arrays.stream(listener.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(EventListener.class))
                .forEach(method -> {
                    // Is a valid event listener
                    Class<?> hookClass = method.getParameterTypes()[0];
                    internalRegister(hookClass, new MListener(method.getAnnotation(EventListener.class), listener, method));
                });
    }
    private void internalRegister(Class<?> hookClass, MListener listener) {
        listeners.put((Class<? extends Event>) hookClass, listener);
    }

}
