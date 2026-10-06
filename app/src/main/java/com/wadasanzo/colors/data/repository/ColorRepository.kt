package com.wadasanzo.colors.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.wadasanzo.colors.data.SanzoData
import com.wadasanzo.colors.data.model.ColorCombination
import com.wadasanzo.colors.data.model.SanzoColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sanzo_preferences")

class ColorRepository(private val context: Context? = null) {

    private val FAVORITE_COLORS_KEY = stringSetPreferencesKey("favorite_color_ids")
    private val FAVORITE_COMBOS_KEY = stringSetPreferencesKey("favorite_combo_ids")

    fun getAllColors(): List<SanzoColor> = SanzoData.allColors

    fun getColorById(id: Int): SanzoColor? = SanzoData.colorById[id]

    fun getAllCombinations(): List<ColorCombination> = SanzoData.allCombinations

    fun getCombinationById(id: Int): ColorCombination? = SanzoData.combinationById[id]

    fun getCombinationsForColor(colorId: Int): List<ColorCombination> {
        val color = getColorById(colorId) ?: return emptyList()
        return color.combinations.mapNotNull { comboId ->
            SanzoData.combinationById[comboId]
        }
    }

    fun searchColors(
        query: String = "",
        swatchFilter: Int? = null
    ): List<SanzoColor> {
        val trimmedQuery = query.trim().lowercase()
        return SanzoData.allColors.filter { color ->
            val matchesQuery = trimmedQuery.isEmpty() ||
                color.name.lowercase().contains(trimmedQuery) ||
                color.hex.lowercase().contains(trimmedQuery)

            val matchesSwatch = swatchFilter == null || color.swatch == swatchFilter

            matchesQuery && matchesSwatch
        }
    }

    fun searchCombinations(
        query: String = "",
        colorCountFilter: Int? = null
    ): List<ColorCombination> {
        val trimmedQuery = query.trim().lowercase()
        return SanzoData.allCombinations.filter { combo ->
            val matchesQuery = trimmedQuery.isEmpty() ||
                "combination #${combo.id}".lowercase().contains(trimmedQuery) ||
                "#${combo.id}".lowercase().contains(trimmedQuery) ||
                combo.id.toString() == trimmedQuery ||
                combo.colors.any { it.name.lowercase().contains(trimmedQuery) || it.hex.lowercase().contains(trimmedQuery) }

            val matchesCount = colorCountFilter == null || combo.colors.size == colorCountFilter

            matchesQuery && matchesCount
        }
    }

    // Favorites persistence via DataStore
    fun getFavoriteColorIds(): Flow<Set<Int>> {
        return context?.dataStore?.data?.map { prefs ->
            prefs[FAVORITE_COLORS_KEY]?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
        } ?: kotlinx.coroutines.flow.flowOf(emptySet())
    }

    suspend fun toggleFavoriteColor(colorId: Int) {
        context?.dataStore?.edit { prefs ->
            val current = prefs[FAVORITE_COLORS_KEY]?.toMutableSet() ?: mutableSetOf()
            val idStr = colorId.toString()
            if (current.contains(idStr)) {
                current.remove(idStr)
            } else {
                current.add(idStr)
            }
            prefs[FAVORITE_COLORS_KEY] = current
        }
    }

    fun getFavoriteComboIds(): Flow<Set<Int>> {
        return context?.dataStore?.data?.map { prefs ->
            prefs[FAVORITE_COMBOS_KEY]?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
        } ?: kotlinx.coroutines.flow.flowOf(emptySet())
    }

    suspend fun toggleFavoriteCombo(comboId: Int) {
        context?.dataStore?.edit { prefs ->
            val current = prefs[FAVORITE_COMBOS_KEY]?.toMutableSet() ?: mutableSetOf()
            val idStr = comboId.toString()
            if (current.contains(idStr)) {
                current.remove(idStr)
            } else {
                current.add(idStr)
            }
            prefs[FAVORITE_COMBOS_KEY] = current
        }
    }
}
