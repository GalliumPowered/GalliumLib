package net.zenoc.gallium.api.world;

public interface World {
    enum Difficulty {
        PEACEFUL(0),
        EASY(1),
        NORMAL(2),
        HARD(3);

        private final int id;

        Difficulty(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Difficulty fromId(int id) {
            return switch (id) {
                case 0 -> PEACEFUL;
                case 1 -> EASY;
                case 3 -> HARD;
                default -> NORMAL;
            };
        }
    }

    /**
     * Get the world name
     * @return the world name
     */
    String getName();
}
