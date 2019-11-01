package com.game.resources

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter

class AssetManagerWrapper(private val missingTexturePath: String) {

    companion object {
        val INSTANCE = AssetManagerWrapper("missing.png")
    }

    var assetManager: AssetManager? = null
        private set

    private var missingTexture: Texture? = null

    fun initialise() {
        if (assetManager != null) {
            disposeAssetManager()
        }

        assetManager = AssetManager()

        val resolver = InternalFileHandleResolver()
        assetManager!!.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
        assetManager!!.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))

        assetManager!!.load(missingTexturePath, Texture::class.java)
        assetManager!!.finishLoading()
        missingTexture = assetManager!!.get(missingTexturePath)
    }

    fun update() {
        assetManager?.update()
    }

    fun disposeAssetManager() {
        assetManager?.dispose()
    }

    fun waitLoadAssets() {
        assetManager?.finishLoading()
    }

    fun loadTexture(path: String) {
        assetManager?.load(path, Texture::class.java)
    }

    fun applyTexture(applyTo: TextureRegion, texturePath: String): Boolean {
        if (applyTo.texture == null) {
            val texture = getTexture(texturePath)
            if (texture == missingTexture) {
                return false
            }

            applyTo.setRegion(texture)
        }

        return true
    }

    fun loadFont(path: String, size: Int) {
        val fontParameters = FreeTypeFontLoaderParameter()
        fontParameters.fontFileName = path
        fontParameters.fontParameters.size = size
        fontParameters.fontParameters.mono = true
        assetManager?.load(path, BitmapFont::class.java, fontParameters)
    }

    fun unload(path: String) {
        assetManager?.unload(path)
    }

    fun getTexture(path: String): Texture = try {
        assetManager?.get<Texture>(path)!!
    } catch (exception: GdxRuntimeException) {
        missingTexture!!
    }

    fun getFont(path: String): BitmapFont = try {
        assetManager?.get<BitmapFont>(path)!!
    } catch (exception: GdxRuntimeException) {
        BitmapFont()
    }

}