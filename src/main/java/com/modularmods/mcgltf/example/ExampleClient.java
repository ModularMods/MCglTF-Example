package com.modularmods.mcgltf.example;

import com.modularmods.mcgltf.MCglTF;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public class ExampleClient implements ClientModInitializer {

	public static ExampleClient INSTANCE;
	
	public float tickDelta;
	
	@Override
	public void onInitializeClient() {
		INSTANCE = this;
		
		WorldRenderEvents.START.register((listener) -> {
			tickDelta = listener.tickDelta();
		});
		
		if(FabricLoader.getInstance().isModLoaded("iris")) {
			BlockEntityRendererRegistry.INSTANCE.register(Example.INSTANCE.exampleBlockEntityType, (dispatcher) -> {
				ExampleBlockEntityRenderer ber = new ExampleBlockEntityRendererIris(dispatcher);
				MCglTF.getInstance().addGltfModelReceiver(ber);
				return ber;
			});
			
			EntityRendererRegistry.INSTANCE.register(Example.INSTANCE.exampleEntityType, (dispatcher, context) -> {
				ExampleEntityRenderer entityRenderer = new ExampleEntityRendererIris(dispatcher);
				MCglTF.getInstance().addGltfModelReceiver(entityRenderer);
				return entityRenderer;
			});
			
			ExampleItemRenderer itemRenderer = new ExampleItemRendererIris() {

				@Override
				public ResourceLocation getModelLocation() {
					return new ResourceLocation("mcgltf", "models/item/water_bottle.gltf");
				}
			};
			MCglTF.getInstance().addGltfModelReceiver(itemRenderer);
			BuiltinItemRendererRegistry.INSTANCE.register(Example.INSTANCE.item, itemRenderer);
			
			ExampleItemRenderer blockItemRenderer = new ExampleItemRendererIris() {

				@Override
				public ResourceLocation getModelLocation() {
					return new ResourceLocation("mcgltf", "models/block/boom_box.gltf");
				}
			};
			MCglTF.getInstance().addGltfModelReceiver(blockItemRenderer);
			BuiltinItemRendererRegistry.INSTANCE.register(Example.INSTANCE.blockItem, blockItemRenderer);
		}
		else {
			BlockEntityRendererRegistry.INSTANCE.register(Example.INSTANCE.exampleBlockEntityType, (dispatcher) -> {
				ExampleBlockEntityRenderer ber = new ExampleBlockEntityRenderer(dispatcher);
				MCglTF.getInstance().addGltfModelReceiver(ber);
				return ber;
			});
			
			EntityRendererRegistry.INSTANCE.register(Example.INSTANCE.exampleEntityType, (dispatcher, context) -> {
				ExampleEntityRenderer entityRenderer = new ExampleEntityRenderer(dispatcher);
				MCglTF.getInstance().addGltfModelReceiver(entityRenderer);
				return entityRenderer;
			});
			
			ExampleItemRenderer itemRenderer = new ExampleItemRenderer() {

				@Override
				public ResourceLocation getModelLocation() {
					return new ResourceLocation("mcgltf", "models/item/water_bottle.gltf");
				}
			};
			MCglTF.getInstance().addGltfModelReceiver(itemRenderer);
			BuiltinItemRendererRegistry.INSTANCE.register(Example.INSTANCE.item, itemRenderer);
			
			ExampleItemRenderer blockItemRenderer = new ExampleItemRenderer() {

				@Override
				public ResourceLocation getModelLocation() {
					return new ResourceLocation("mcgltf", "models/block/boom_box.gltf");
				}
			};
			MCglTF.getInstance().addGltfModelReceiver(blockItemRenderer);
			BuiltinItemRendererRegistry.INSTANCE.register(Example.INSTANCE.blockItem, blockItemRenderer);
		}
	}

}
