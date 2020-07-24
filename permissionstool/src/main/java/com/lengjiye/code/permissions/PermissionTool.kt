package com.lengjiye.code.permissions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class PermissionTool {

    companion object {
        private var TAG = PermissionTool::class.java.simpleName
    }

    constructor(activity: FragmentActivity) : super() {
        getPermissionFragment(activity.supportFragmentManager)
    }

    constructor(fragment: Fragment) : super() {
        getPermissionFragment(fragment.childFragmentManager)
    }

    private fun getPermissionFragment(fragmentManager: FragmentManager): PermissionFragment? {
        var permissionFragment: PermissionFragment? = findPermissionFragment(fragmentManager)
        val isNewInstance = permissionFragment == null
        if (isNewInstance) {
            permissionFragment = PermissionFragment()
            fragmentManager
                .beginTransaction()
                .add(permissionFragment, TAG)
                .commitNow()
        }
        return permissionFragment
    }

    private fun findPermissionFragment(fragmentManager: FragmentManager): PermissionFragment? {
        return fragmentManager.findFragmentByTag(TAG) as PermissionFragment?
    }

    class Builder {

//        fun builder(): PermissionTool {
//
//        }
    }
}