package com.dozingcatsoftware.vectorcamera

import android.renderscript.Allocation
import android.util.Size

data class CameraImage(val singleYuvAllocation: Allocation?,
                       val planarYuvAllocations: PlanarYuvAllocations?,
                       val orientation: ImageOrientation, val status: CameraStatus,
                       val timestamp: Long, val displaySize: Size = zeroSize) {

    fun width(): Int {
        return singleYuvAllocation?.type?.x ?: planarYuvAllocations!!.y.type.x
    }

    fun height(): Int {
        return singleYuvAllocation?.type?.y ?: planarYuvAllocations!!.y.type.y
    }

    fun size() = Size(width(), height())

    fun withDisplaySize(ds: Size): CameraImage {
        return CameraImage(
                singleYuvAllocation, planarYuvAllocations, orientation, status, timestamp, ds)
    }

    companion object {
        private val zeroSize = Size(0, 0)

        fun withAllocation(allocation: Allocation, orientation: ImageOrientation,
                           status: CameraStatus, timestamp: Long,
                           displaySize: Size = zeroSize): CameraImage {
            return CameraImage(allocation, null, orientation, status, timestamp, displaySize)
        }

        fun withAllocationSet(yuv: PlanarYuvAllocations, orientation: ImageOrientation,
                              status: CameraStatus, timestamp: Long,
                              displaySize: Size = zeroSize): CameraImage {
            return CameraImage(null, yuv, orientation, status, timestamp, displaySize)
        }
    }
}