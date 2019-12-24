/*
 * Copyright (C) 2019 Thomas Panton
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.game.resources

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter
import com.badlogic.gdx.utils.GdxRuntimeException
import com.github.dwursteisen.libgdx.aseprite.Aseprite
import com.github.dwursteisen.libgdx.aseprite.AsepriteJson
import com.github.dwursteisen.libgdx.aseprite.AsepriteJsonLoader
import com.github.dwursteisen.libgdx.aseprite.AsepriteLoader

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
        assetManager!!.setLoader(AsepriteJson::class.java, AsepriteJsonLoader(resolver))
        assetManager!!.setLoader(Aseprite::class.java, AsepriteLoader(resolver))
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

    fun loadFont(path: String, size: Int) {
        val fontParameters = FreeTypeFontLoaderParameter()
        fontParameters.fontFileName = path
        fontParameters.fontParameters.size = size
        fontParameters.fontParameters.mono = true
        assetManager?.load(path, BitmapFont::class.java, fontParameters)
    }

    fun loadAnimation(path: String) {
        assetManager?.load(path, Aseprite::class.java)
    }

    fun loadSound(path: String) {
        assetManager?.load(path, Sound::class.java)
    }

    fun fetchAnimation(path: String): Aseprite {
        assetManager!!.load(path, Aseprite::class.java)
        assetManager!!.finishLoadingAsset<Aseprite>(path)
        return assetManager!!.get<Aseprite>(path)
    }

    fun unload(path: String) {
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
        assetManager!!.get<Texture>(path)
    } catch (exception: GdxRuntimeException) {
        missingTexture!!
    }

    fun getFont(path: String): BitmapFont = try {
        assetManager!!.get<BitmapFont>(path)
    } catch (exception: GdxRuntimeException) {
        BitmapFont()
    }

    fun getAnimation(path: String): Aseprite =
            assetManager!!.get<Aseprite>(path)

    fun getSound(path: String): Sound =
        assetManager!!.get<Sound>(path)

}