package com.lengjiye.code.project.model

import com.lengjiye.code.project.service.ProjectService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.ServiceHolder

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
class ProjectModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = ProjectModel()
    }

    private fun getService(): ProjectService? {
        return ServiceHolder.singleton.getService(ProjectService::class.java)
    }
}
