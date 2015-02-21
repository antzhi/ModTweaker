package modtweaker2.mods.forestry.handlers;

import java.util.HashMap;
import java.util.List;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker2.util.BaseListAddition;
import modtweaker2.util.BaseListRemoval;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import forestry.factory.gadgets.MachineCentrifuge.Recipe;
import forestry.factory.gadgets.MachineCentrifuge.RecipeManager;
import static modtweaker2.helpers.InputHelper.*;
import static modtweaker2.helpers.StackHelper.*;


@ZenClass("mods.forestry.Centrifuge")
public class Centrifuge {

	@ZenMethod
	public static void addRecipe(int timePerItem, IItemStack itemInput, IItemStack[] output, int[] chances) {
		HashMap<ItemStack, Integer> products = new HashMap<ItemStack, Integer>();
		// products.put(toStack(output[0]), chances[0]);
		int i = 0;
		for (IItemStack product : output) {
			products.put(toStack(product), chances[i]);
			i++;
		}
		MineTweakerAPI.apply(new Add(new Recipe(timePerItem, toStack(itemInput), products)));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack itemInput) {
		MineTweakerAPI.apply(new Remove(RecipeManager.recipes, toStack(itemInput)));
	}

	/*
	Implements the actions to add the recipe
	*/
	private static class Add extends BaseListAddition {

		public Add(Recipe recipe) {
			super("Forestry Centrifuge", RecipeManager.recipes, recipe);
		}
		@Override
		public String getRecipeInfo() {
			return " Input:" + ((Recipe) recipe).resource.getDisplayName();
		}
	}

	/*
	Implements the actions to remove the recipe
	*/
	private static class Remove extends BaseListRemoval {

		public Remove(List list, ItemStack input) {
			super("Forestry Centrifuge", RecipeManager.recipes, input);
		}

		@Override
		public void apply() {
			for (Recipe r : RecipeManager.recipes) {
				if (r.matches(stack)) {
					recipe = r;
					RecipeManager.recipes.remove(r);
					break;
				}
			}
		}

		@Override
		public String getRecipeInfo() {
			return " Input:" + stack.getDisplayName();
		}
	}
}