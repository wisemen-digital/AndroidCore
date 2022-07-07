package com.example.coredemo.ui.networking

import be.appwise.networking.base.BaseRestClient
import com.appham.mockinizer.RequestFilter
import com.appham.mockinizer.mockinize
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse

object ApiRestClient : BaseRestClient() {

    override val protectedClient: Boolean get() = false
    override fun getBaseUrl() = "https://www.google.be"

    val retrofitService: ApiService by lazy {
        getRetrofit.create(ApiService::class.java)
    }

    override fun createHttpClient(): OkHttpClient {
        return super.createHttpClient().newBuilder()
            .mockinize(mocks)
            .build()
    }

    private val pokemons: Map<RequestFilter, MockResponse> = mapOf(
        RequestFilter("/pokemons") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    [
                        {
                            "id":"1",
                            "name":"Bulbasaur",
                            "icon": "https://serebii.net/pokedex-swsh/icon/001.png"
                        },
                        {
                            "id":"2",
                            "name":"Ivysaur",
                            "icon": "https://serebii.net/pokedex-swsh/icon/002.png"
                        },
                        {
                            "id":"3",
                            "name":"Venusaur",
                            "icon": "https://serebii.net/pokedex-swsh/icon/003.png"
                        },
                        {
                            "id":"4",
                            "name":"Charmander",
                            "icon": "https://serebii.net/pokedex-swsh/icon/004.png"
                        },
                        {
                            "id":"5",
                            "name":"Charmeleon",
                            "icon": "https://serebii.net/pokedex-swsh/icon/005.png"
                        },
                        {
                            "id":"6",
                            "name":"Charizard",
                            "icon": "https://serebii.net/pokedex-swsh/icon/006.png"
                        },
                        {
                            "id":"7",
                            "name":"Squirtle",
                            "icon": "https://serebii.net/pokedex-swsh/icon/007.png"
                        },
                        {
                            "id":"8",
                            "name":"Wartortle",
                            "icon": "https://serebii.net/pokedex-swsh/icon/008.png"
                        },
                        {
                            "id":"9",
                            "name":"Blastoise",
                            "icon": "https://serebii.net/pokedex-swsh/icon/009.png"
                        },
                        {
                            "id":"10",
                            "name":"Caterpie",
                            "icon": "https://serebii.net/pokedex-swsh/icon/010.png"
                        },
                        {
                            "id":"11",
                            "name":"Metapod",
                            "icon": "https://serebii.net/pokedex-swsh/icon/011.png"
                        },
                        {
                            "id":"12",
                            "name":"Butterfree",
                            "icon": "https://serebii.net/pokedex-swsh/icon/012.png"
                        }
                    ]
                """.trimIndent()
            )
        },
    )

    private val pokemonDetails: Map<RequestFilter, MockResponse> = mapOf(
        RequestFilter("/pokemons/1") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    {
                        "id":"1",
                        "name":"Bulbasaur",
                        "type_1":"Grass"
                    }
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/2") to MockResponse().let {
            it.setResponseCode(400)
            it.setBody(
                """
                    { "exception":"someException", "message":"Wrong pokemon"}
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/3") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    {
                        "id":"3",
                        "name":"Venusaur",
                        "type_1":"Poison"
                    }
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/4") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    {
                        "id":"4",
                        "name":"Charmander",
                        "type_1":"Fire"
                    }
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/5") to MockResponse().let {
            it.setResponseCode(400)
            it.setBody(
                """
                    { "exception":"someException", "message":"Wrong pokemon"}
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/6") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    {
                        "id":"6",
                        "name":"Charizard",
                        "type_1":"Dragon"
                    }
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/7") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    {
                        "id":"7",
                        "name":"Squirtle",
                        "type_1":"Water"
                    }
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/8") to MockResponse().let {
            it.setResponseCode(400)
            it.setBody(
                """
                    { "exception":"someException", "message":"Wrong pokemon"}
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/9") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    {
                        "id":"9",
                        "name":"Blastoise",
                        "type_1":"Canon"
                    }
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/10") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    {
                        "id":"10",
                        "name":"Caterpie",
                        "type_1":"Bug"
                    }
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/11") to MockResponse().let {
            it.setResponseCode(400)
            it.setBody(
                """
                    { "exception":"someException", "message":"Wrong pokemon"}
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/12") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    {
                        "id":"12",
                        "name":"Butterfree",
                        "type_1":"Bug"
                    }
                """.trimIndent()
            )
        }
    )

    private val pokemonMoves: Map<RequestFilter, MockResponse> = mapOf(
        RequestFilter("/pokemons/1/moves") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    [
                        "Tackle",
                        "Quick Attack",
                        "Poison Powder"
                    ]
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/2/moves") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    [
                        "Vine Whip",
                        "Sleep Powder",
                        "Feint Attack"
                    ]
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/3/moves") to MockResponse().let {
            it.setResponseCode(400)
            it.setBody(
                """
                    { "exception":"someException", "message":"Wrong moves"}
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/4/moves") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    [
                        "Tackle",
                        "Quick Attack",
                        "Ember"
                    ]
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/5/moves") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    [
                        "Tackle",
                        "Fire Spin",
                        "Leech Seed"
                    ]
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/6/moves") to MockResponse().let {
            it.setResponseCode(400)
            it.setBody(
                """
                    { "exception":"someException", "message":"Wrong moves"}
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/7/moves") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    [
                        "Sun Beam",
                        "Grass Whip",
                        "Leech Seed"
                    ]
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/8/moves") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    [
                        "Sun Beam",
                        "Grass Whip",
                        "Leech Seed"
                    ]
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/9/moves") to MockResponse().let {
            it.setResponseCode(400)
            it.setBody(
                """
                    { "exception":"someException", "message":"Wrong moves"}
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/10/moves") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    [
                        "Tackle",
                        "String Shot"
                    ]
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/11/moves") to MockResponse().let {
            it.setResponseCode(200)
            it.setBody(
                """
                    [
                        "Tackle",
                        "Harden"
                    ]
                """.trimIndent()
            )
        },
        RequestFilter("/pokemons/12/moves") to MockResponse().let {
            it.setResponseCode(400)
            it.setBody(
                """
                    { "exception":"someException", "message":"Wrong moves"}
                """.trimIndent()
            )
        }
    )

    private val mocks: Map<RequestFilter, MockResponse> = pokemons + pokemonDetails + pokemonMoves
}