package com.timlee9024.mcgltf.example;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.timlee9024.mcgltf.IGltfModelReceiver;
import com.timlee9024.mcgltf.RenderedGltfModel;

import de.javagl.jgltf.model.GltfAnimations;
import de.javagl.jgltf.model.animation.Animation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemStackTileEntityRendererItemExample extends ItemStackTileEntityRenderer implements IGltfModelReceiver {

	protected List<Runnable> commands;
	
	protected List<Animation> animations;
	
	@Override
	public ResourceLocation getModelLocation() {
		return new ResourceLocation("mcgltf", "models/item/water_bottle.gltf");
	}

	@Override
	public void onModelLoaded(RenderedGltfModel renderedModel) {
		commands = renderedModel.sceneCommands.get(0);
		animations = GltfAnimations.createModelAnimations(renderedModel.gltfModel.getAnimationModels());
	}

	@Override
	public void renderByItem(ItemStack p_239207_1_, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack p_239207_3_, IRenderTypeBuffer p_239207_4_, int p_239207_5_, int p_239207_6_) {
		if(commands != null) {
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			
			switch(p_239207_2_) {
			case THIRD_PERSON_LEFT_HAND:
			case THIRD_PERSON_RIGHT_HAND:
			case HEAD:
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				
				GL13.glActiveTexture(GL13.GL_TEXTURE1);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL13.glActiveTexture(GL13.GL_TEXTURE2);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				
				GL13.glMultiTexCoord2s(GL13.GL_TEXTURE1, (short)(p_239207_6_ & '\uffff'), (short)(p_239207_6_ >> 16 & '\uffff'));
				GL13.glMultiTexCoord2s(GL13.GL_TEXTURE2, (short)(p_239207_5_ & '\uffff'), (short)(p_239207_5_ >> 16 & '\uffff'));
				break;
			case FIRST_PERSON_LEFT_HAND:
			case FIRST_PERSON_RIGHT_HAND:
			case GROUND:
			case FIXED:
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				
				GL13.glActiveTexture(GL13.GL_TEXTURE2);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				
				GL13.glMultiTexCoord2s(GL13.GL_TEXTURE2, (short)(p_239207_5_ & '\uffff'), (short)(p_239207_5_ >> 16 & '\uffff'));
				break;
			default:
				break;
			}
			
			RenderSystem.multMatrix(p_239207_3_.last().pose());
			Minecraft mc = Minecraft.getInstance();
			for(Animation animation : animations) {
				animation.update(net.minecraftforge.client.model.animation.Animation.getWorldTime(mc.level, net.minecraftforge.client.model.animation.Animation.getPartialTickTime()) % animation.getEndTimeS());
			}
			commands.forEach((command) -> command.run());
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}

}
