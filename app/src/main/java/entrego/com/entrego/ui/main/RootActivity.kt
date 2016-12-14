package entrego.com.entrego.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.Observable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatDrawableManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import entrego.com.entrego.R
import entrego.com.entrego.location.LocationService
import entrego.com.entrego.location.LocationTracker
import entrego.com.entrego.binding.DeliveryInstance
import entrego.com.entrego.storage.preferences.EntregoStorage
import entrego.com.entrego.ui.auth.AuthActivity
import entrego.com.entrego.ui.main.account.AccountFragment
import entrego.com.entrego.ui.main.drawer.DrawerFragment
import entrego.com.entrego.ui.main.home.HomeFragment
import entrego.com.entrego.ui.main.home.model.DeliveryRequest
import entrego.com.entrego.util.Logger
import entrego.com.entrego.util.event_bus.LogoutEvent
import entrego.com.entrego.util.ui.ViewPagerAdapter
import kotlinx.android.synthetic.main.content_drawer.*
import kotlinx.android.synthetic.main.content_root.*

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        setupViewPager(main_viewpager)

        val fragment = DrawerFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.root_drawer_container, fragment)
                .commit()

        main_tabs.setupWithViewPager(main_viewpager)
        setupTabIcons()
        root_btn_log_out.setOnClickListener {
            EventBus.getDefault().post(LogoutEvent())
            finish()
        }

        EventBus.getDefault().register(this)

        val token = EntregoStorage(this).getToken()
        DeliveryRequest.requestDelivery(token, null)


        Handler(Looper.getMainLooper()).postDelayed({ DeliveryInstance.getInstance().update(null) }, 5000)
        Handler(Looper.getMainLooper()).postDelayed({ DeliveryRequest.requestDelivery(token, null) }, 7000)
//        Handler(Looper.getMainLooper()).postDelayed({ DeliveryInstance.getInstance().route.start.address= "asdads" }, 8000)
    }

    override fun onStart() {
        super.onStart()
        checkLocationPermission()

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private val tabIcons = intArrayOf(
            R.drawable.ic_home,
            R.drawable.ic_account,
            R.drawable.ic_attach_money,
            R.drawable.ic_star_border)

    private val tabTitles = intArrayOf(
            R.string.ui_title_home,
            R.string.ui_title_account,
            R.string.ui_title_income,
            R.string.ui_title_score)

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFrag(HomeFragment(), getString(tabTitles[0]))
        adapter.addFrag(AccountFragment(), getString(tabTitles[1]))
        adapter.addFrag(Fragment(), getString(tabTitles[2]))
        adapter.addFrag(Fragment(), getString(tabTitles[3]))

        viewPager.adapter = adapter
    }

    fun setupTabIcons() {

        for (i in 0..tabTitles.size - 1) {
            val nextOne = LayoutInflater.from(this).inflate(R.layout.tab_view, null)
            (nextOne.findViewById(R.id.tab_text) as TextView).text = getString(tabTitles[i])
            val icon = AppCompatDrawableManager.get().getDrawable(applicationContext, tabIcons[i])
            (nextOne.findViewById(R.id.tab_icon) as ImageView).setImageDrawable(icon)
            main_tabs.getTabAt(i)?.customView = nextOne
        }
    }


    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.root, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_faq) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    fun checkLocationPermission() {

        val permissionFine = ContextCompat.checkSelfPermission(RootActivity@ this,
                Manifest.permission.ACCESS_FINE_LOCATION)

        when (permissionFine) {
            PackageManager.PERMISSION_GRANTED -> {
                startLocationUpdates()
            }
            PackageManager.PERMISSION_DENIED -> {
                ActivityCompat.requestPermissions(RootActivity@ this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        HomeFragment.REQUEST_ACCESS_FINE_LOCATION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {

            HomeFragment.REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates()
                } else {
                    //TODO: Show blocker

                }
            }
        }

    }

    fun startLocationUpdates() {
        LocationTracker.startLocationTracker(applicationContext)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogoutEvent(event: LogoutEvent) {

        EntregoStorage(this).setToken("")
        val intent = Intent(this, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)

    }


}
