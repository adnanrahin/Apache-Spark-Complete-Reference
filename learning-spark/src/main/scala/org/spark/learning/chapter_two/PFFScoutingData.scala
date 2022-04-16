package org.spark.learning.chapter_two

case class PFFScoutingData(
                            gameId: Long,
                            playId: Long,
                            snapDetail: String,
                            snapTime: Double,
                            operationTime: Double,
                            hangTime: Double,
                            kickType: String,
                            kickDirectionIntended: String,
                            kickDirectionActual: String,
                            returnDirectionIntended: String,
                            returnDirectionActual: String,
                            missedTackler: String,
                            assistTackler: String,
                            tackler: String,
                            kickoffReturnFormation: String,
                            gunners: String,
                            puntRushers: String,
                            specialTeamsSafeties: String,
                            vises: String,
                            kickContactType: String
                          )
