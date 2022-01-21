package ru.surfstudio.mvi.core.middleware

/**
 * Basic object in terms of MVI.
 * Transforms the stream of events upcoming from the EventHub and returns the stream of transformed events.
 *
 * Is used to transform UI events into service layer events.
 * For example for starting network request after pressing UI button.
 *
 * @param InputStream   type of income stream
 * @param OutputStream  type of outcome stream
 */
interface Middleware<InputStream, OutputStream> {

    fun transform(eventStream: InputStream): OutputStream
}