package com.game.resources

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

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

    fun unloadTexture(path: String) {
        assetManager?.unload(path)
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

    fun getTexture(path: String): Texture = try {
        assetManager?.get<Texture>(path)!!
    } catch (exception: GdxRuntimeException) {
        missingTexture ?: error("Asset manager wrapper not yet initialised")
    }

}