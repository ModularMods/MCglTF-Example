# MCglTF-Example
 Example usages for MCglTF
 
![preview](https://user-images.githubusercontent.com/39574697/157580750-55867059-5fe8-4bcb-855c-039121b81410.png)
## Model Source
https://github.com/KhronosGroup/glTF-Sample-Models/tree/master/2.0#gltf-20-sample-models

- Boom Box and Water Bottle by Microsoft
- Cesium Man by Cesium

In order to adapt into BSL Shaders' SEUS/Old PBR format, some change were made:
- All normal textures had been converted from OpenGL format (Y+) to DirectX format (Y-) by flipping green channel.
- `Occlusion(R)Roughness(G)Metallic(B)` textures and `Emissive color(RGB)` textures had been edited and combined into `Glossiness(R)Metallic(G)Emissive strength(B)` textures for specular map.
## Additonal Note About Setup This Project
1. Before build MCglTF for developmental environment. There is two ways you can choose to avoid gradle compilation error for OptiFine code reference.
	- Simply replace [this line](https://github.com/TimLee9024/MCglTF/blob/a719a89175d7fd09dc609a2db7c62e23dc5b57e0/src/main/java/com/timlee9024/mcgltf/MCglTF.java#L277) with `return false;` in `isShaderModActive()`.
	- Download OptiFine and delete everything except files and folders inside `net/optifine/` of OptiFine JAR. Then put modified OptiFine JAR into a newly created folder named `libs` in the same dir level as `src` of MCglTF project.
2. Build MCglTF with "gradlew jar" to create a [deobuscated version](https://forums.minecraftforge.net/topic/81617-1152-eclipse-and-gradle-how-to-use-jar-from-another-project-and-import-solved) of MCglTF.
3. Create a folder named `libs` in the same dir level as `src` of MCglTF-Example project.
4. Put deobuscated version of MCglTF into the `libs` folder.
5. In Eclipse IDE, add MCglTF jar as `Referenced Libraries` via `Project > Properties > Java Build Path > Libraries > Add JARs`.
