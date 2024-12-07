package com.lkoskela.complicationtaptest.complication

import android.app.PendingIntent
import android.content.Intent
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceService
import androidx.wear.watchface.complications.datasource.ComplicationDataTimeline
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import androidx.wear.watchface.complications.datasource.SuspendingComplicationDataSourceService
import androidx.wear.watchface.complications.datasource.SuspendingTimelineComplicationDataSourceService
import androidx.wear.watchface.complications.datasource.TimeInterval
import androidx.wear.watchface.complications.datasource.TimelineEntry
import com.lkoskela.complicationtaptest.presentation.MainActivity
import java.time.Instant
import java.util.Calendar

/**
 * Skeleton for complication data source that returns short text.
 */
class MainComplicationService : ComplicationDataSourceService() {

    override fun getPreviewData(type: ComplicationType): ComplicationData? {
        if (type != ComplicationType.SHORT_TEXT) {
            return null
        }
        return createComplicationData()
    }

    override fun onComplicationRequest(
        request: ComplicationRequest,
        listener: ComplicationRequestListener
    ) {
        val timelineEntries = listOf(
            TimelineEntry(
                TimeInterval(
                    Instant.now().minusSeconds(3600),
                    Instant.now().plusSeconds(3600)
                ), createComplicationData()
            )
        )
        val timeline = ComplicationDataTimeline(
            createComplicationData(), timelineEntries
        )
        listener.onComplicationDataTimeline(timeline)
    }

    private fun createComplicationData(): ComplicationData {
        val pi = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        return ShortTextComplicationData.Builder(
            text = PlainComplicationText.Builder("foo").build(),
            contentDescription = PlainComplicationText.Builder("foo").build()
        ).setTapAction(pi).build()
    }
}