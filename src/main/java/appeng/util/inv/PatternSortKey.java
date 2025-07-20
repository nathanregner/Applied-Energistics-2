package appeng.util.inv;

import appeng.crafting.pattern.EncodedPatternItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

final class PatternSortKey implements Comparable<PatternSortKey> {

    public static final @NotNull PatternSortKey EMPTY = new PatternSortKey(null);

    public static PatternSortKey of(ItemStack stack) {
        if (stack.isEmpty()) {
            return PatternSortKey.EMPTY;
        }
        var item = stack.getItem();
        if (stack.getItem() instanceof EncodedPatternItem<?> pattern) {
            item = pattern.getOutput(stack).getItem();
        }
        return new PatternSortKey(BuiltInRegistries.ITEM.getKey(item));
    }


    private final @Nullable ResourceLocation item;

    private PatternSortKey(@Nullable ResourceLocation item) {
        this.item = item;
    }

    @Override
    public int compareTo(@NotNull PatternSortKey other) {
        if (this.item() == other.item()) return 0;
        if (this.item() == null) return 1;
        if (other.item() == null) return -1;
        return this.item().compareNamespaced(other.item());
    }

    public @Nullable ResourceLocation item() {
        return item;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PatternSortKey) obj;
        return Objects.equals(this.item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }

    @Override
    public String toString() {
        return "PatternSortKey[" +
                "item=" + item + ']';
    }

}
