package entrego.com.android.ui.main

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatDrawableManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import entrego.com.android.R
import entrego.com.android.location.TrackService
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.account.AccountFragment
import entrego.com.android.ui.auth.AuthActivity
import entrego.com.android.ui.faq.FaqListActivity
import entrego.com.android.ui.incomes.IncomesFragment
import entrego.com.android.ui.main.dialog.GPSRequiredFragment
import entrego.com.android.ui.main.dialog.LocationRequiredFragment
import entrego.com.android.ui.main.drawer.DrawerFragment
import entrego.com.android.ui.main.home.HomeFragment
import entrego.com.android.ui.main.home.model.DeliveryRequest
import entrego.com.android.ui.score.ScoreFragment
import entrego.com.android.util.event_bus.LogoutEvent
import entrego.com.android.util.isGpsEnable
import entrego.com.android.util.ui.ViewPagerAdapter
import entrego.com.android.web.socket.SocketService
import kotlinx.android.synthetic.main.app_bar_root.*
import kotlinx.android.synthetic.main.content_drawer.*
import kotlinx.android.synthetic.main.content_root.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RootActivity : AppCompatActivity() {

    companion object {
        val REQUEST_ACCESS_FINE_LOCATION = 0x811
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        setupLayouts()
        startService(Intent(this, SocketService::class.java))
        checkLocationPermission()
        EventBus.getDefault().register(this)
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(mGpsSwitchStateReceiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(mGpsSwitchStateReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    fun setupLayouts() {
        setSupportActionBar(root_toolbar)
        supportActionBar?.title = ""
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, root_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        root_drawer_container.setOnClickListener { }
        setupViewPager(main_viewpager)

        val fragment = DrawerFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.root_drawer_container, fragment)
                .commit()
        main_tabs.setupWithViewPager(main_viewpager)
        setupTabIcons()
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
        adapter.addFrag(IncomesFragment(), getString(tabTitles[2]))
        adapter.addFrag(ScoreFragment(), getString(tabTitles[3]))

        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 4
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
        menuInflater.inflate(R.menu.menu_root, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_faq) {
            startActivity(Intent(this, FaqListActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun requestDelivery() {
        val token = EntregoStorage.getToken()
        DeliveryRequest.requestDelivery(token, null)
    }


    fun checkLocationPermission() {
        if (isLocationPermissionGuaranteed())
            startLocationUpdates()
        else {
            ActivityCompat.requestPermissions(
                    RootActivity@ this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates()
                } else {
                    LocationRequiredFragment.show(supportFragmentManager)
                }
            }
        }
    }

    fun startLocationUpdates() {
        if (isGpsEnable()) {
            requestDelivery()
        } else {
            GPSRequiredFragment.show(supportFragmentManager)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogoutEvent(event: LogoutEvent) {
        EntregoStorage.setToken("")
        val intent = AuthActivity.getIntent(this)
        stopService(Intent(this, SocketService::class.java))
        startActivity(intent)
        finish()
    }

    fun isLocationPermissionGuaranteed(): Boolean =
            ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


    val mGpsSwitchStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctx: Context, p1: Intent?) {
            if (!isGpsEnable())
                GPSRequiredFragment.show(supportFragmentManager)
            else
                checkLocationPermission()
        }
    }


}
