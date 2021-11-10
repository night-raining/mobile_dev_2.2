package com.example.mobileapps22

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mobileapps22.lab1.Lab1Fragment
import com.example.mobileapps22.lab2.Lab2Fragment
import com.example.mobileapps22.lab3.Lab3Fragment
import com.example.mobileapps22.lab4.Lab4Fragment
import com.example.mobileapps22.lab5.Lab5Fragment
import com.example.mobileapps22.lab6.Lab6Fragment
import com.example.mobileapps22.lab7.Lab7Fragment
import com.example.mobileapps22.lab8.Lab8Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar:Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val viewPager:ViewPager = findViewById(R.id.viewPager)
        val tabLayout:TabLayout = findViewById(R.id.tabLayout)
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        val lab1Fragment = Lab1Fragment()
        val lab2Fragment = Lab2Fragment()
        val lab3Fragment = Lab3Fragment()
        val lab4Fragment = Lab4Fragment()
        val lab5Fragment = Lab5Fragment()
        val lab6Fragment = Lab6Fragment()
        val lab7Fragment = Lab7Fragment()
        val lab8Fragment = Lab8Fragment()


//        val fragmentList = arrayListOf(lab1Fragment, lab2Fragment, lab3Fragment)

        tabLayout.setupWithViewPager(viewPager)

        val viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )

//        fillFragmentsAdapter(viewPagerAdapter, fragmentList)
        viewPagerAdapter.addFragment(lab1Fragment, "Lab 1")
        viewPagerAdapter.addFragment(lab2Fragment, "Lab 2")
        viewPagerAdapter.addFragment(lab3Fragment, "Lab 3")
        viewPagerAdapter.addFragment(lab4Fragment, "Lab 4")
        viewPagerAdapter.addFragment(lab5Fragment, "Lab 5")
        viewPagerAdapter.addFragment(lab6Fragment, "Lab 6")
        viewPagerAdapter.addFragment(lab7Fragment, "Lab 7")
        viewPagerAdapter.addFragment(lab8Fragment, "Lab 8")

        viewPager.adapter = viewPagerAdapter
    }

//    /**
//     * Fulfilling adapter with lab fragments. Each fragment is a lab
//     */
//    private fun fillFragmentsAdapter(viewPagerAdapter: ViewPagerAdapter, fragmentList: ArrayList<Fragment>): Unit {
//
//    }

    private class ViewPagerAdapter(supportFragmentManager: FragmentManager, behavior: Int): FragmentPagerAdapter(
        supportFragmentManager,
        behavior
    ) {
        private val fragments: ArrayList<Fragment> = arrayListOf()
        private val fragmentTitles: ArrayList<String> = arrayListOf()

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitles[position]
        }

        fun addFragment(fragment: Fragment, title: String): Unit {
            fragments.add(fragment)
            fragmentTitles.add(title)
        }
    }
}