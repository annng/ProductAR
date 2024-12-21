package com.pam.product.core.route

sealed class NavigationDestinations(val route: String) {
    object ProductRootRoute : NavigationDestinations(route = "product")


    object ProductListRoute : NavigationDestinations(route = "product_list")
    object ProductDetailRoute : NavigationDestinations(route = "product_detail")
    object ProductArRoute : NavigationDestinations(route = "onboard")
    object NoteDetailScreen :
        NavigationDestinations(route = "notes_app_note_detail_screen?noteId={noteId}") {
        const val NAV_ARG_NOTE_ID = "noteId"
        fun buildRoute(
            noteId: String? = null
        ): String {
            return noteId?.let {
                "notes_app_note_detail_screen?noteId=$noteId"
            } ?: "notes_app_note_detail_screen"
        }
    }
}