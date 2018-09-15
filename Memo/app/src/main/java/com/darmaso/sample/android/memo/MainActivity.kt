package com.darmaso.sample.android.memo

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import java.io.File

class MainActivity : AppCompatActivity(),
        FilesListFragment.OnFileSelectListener,
        InputFragment.OnFileOutputListener {

    // ファイルをセーブした時のコールバック
    override fun onFileOutput() {
        val fragment = supportFragmentManager.findFragmentById(R.id.list) as FilesListFragment
        fragment.show()
    }

    // ファイルのリストをタップした時のコールバック
    override fun onFileSelected(file: File) {
        val fragment = supportFragmentManager.findFragmentById(R.id.input) as InputFragment
        fragment.show(file)
    }

    // ナビドロ状態操作用オブジェクト
    private var drawerToggle : ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (hasPermission()) setupViews()
    }

    // Activityの生成が終わった後に呼ばれる
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // ドロワーのトグル状態を同期
        drawerToggle?.syncState()
    }

    // 画面が回転するなど、状態が変化した時に呼ばれる
    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        // 状態変化をドロワーに伝える
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    private fun setupDrawer(drawer: DrawerLayout) {
        val toggle = ActionBarDrawerToggle(this, drawer, R.string.app_name, R.string.app_name)
        // ドロワーのトグルを有効にする
        toggle.isDrawerIndicatorEnabled = true
        // 開いだり閉じたりのコールバック
        drawer.addDrawerListener(toggle)

        drawerToggle = toggle

        // アクションバーの設定
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    // オプションメニューがタップされた時に呼ばれる
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // ドロワーに伝える
        if (drawerToggle?.onOptionsItemSelected(item) == true) {
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun setupViews() {
        setContentView(R.layout.activity_main)

        // レイアウトからドロワーを探す
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        // レイアウト中にドロワーがある場合にだけ行う処理
        if (drawerLayout != null) {
            setupDrawer(drawerLayout)
        }
    }

    private fun hasPermission() : Boolean {
        // パーミッションを持っているか確認
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 持っていないので要求
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (!grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupViews()
            drawerToggle?.syncState()
        } else {
            finish()
        }
    }
}
