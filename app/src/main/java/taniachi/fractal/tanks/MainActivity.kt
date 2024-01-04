package taniachi.fractal.tanks

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_DPAD_DOWN
import android.view.KeyEvent.KEYCODE_DPAD_LEFT
import android.view.KeyEvent.KEYCODE_DPAD_RIGHT
import android.view.KeyEvent.KEYCODE_DPAD_UP
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import taniachi.fractal.tanks.Direction.DOWN
import taniachi.fractal.tanks.Direction.LEFT
import taniachi.fractal.tanks.Direction.RIGHT
import taniachi.fractal.tanks.Direction.UP
import taniachi.fractal.tanks.databinding.ActivityMainBinding

lateinit var binding: ActivityMainBinding
const val CELL_SIZE = 50
const val MOVE_DIST = 50


class MainActivity : AppCompatActivity() {

    private var editMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="Танчики"
    }

    private val gridDrawer by lazy {
        GridDrawer(this)
    }

    private fun switchEditMode()
    {
        if (editMode) {
            gridDrawer.removeGrid()
            binding.materialsContainer.visibility = INVISIBLE
        }
        else
        {
            gridDrawer.drawGrid()
            binding.materialsContainer.visibility = VISIBLE
        }
        editMode = !editMode
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.settings,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when (item.itemId)
        {
            R.id.menu_settings ->
            {
                switchEditMode()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Do moving based on input
    override fun onKeyDown(keyCode: Int, event: KeyEvent?):Boolean
    {
        when (keyCode)
        {
            KEYCODE_DPAD_UP -> move(UP)
            KEYCODE_DPAD_DOWN -> move(DOWN)
            KEYCODE_DPAD_RIGHT -> move(RIGHT)
            KEYCODE_DPAD_LEFT -> move(LEFT)
        }
        return super.onKeyDown(keyCode, event)
    }

    // Function for moving
    private fun move(direction: Direction)
    {
        when(direction)
        {
            UP ->
                {
                    binding.myTank.rotation = 0f
                    if (binding.myTank.translationY > 0)
                    {
                        binding.myTank.translationY -= MOVE_DIST
                    }
                }
            DOWN ->
                {
                    binding.myTank.rotation = 180f
                    if (binding.myTank.translationY + binding.myTank.height  < binding.container.height / MOVE_DIST * MOVE_DIST)
                    {
                        binding.myTank.translationY += MOVE_DIST
                    }
                }
            RIGHT ->
                {
                    binding.myTank.rotation = 90f

                    if (binding.myTank.translationX + binding.myTank.width  < binding.container.width / MOVE_DIST * MOVE_DIST)
                    {
                        binding.myTank.translationX += MOVE_DIST
                    }
                }
            LEFT ->
                {
                    binding.myTank.rotation = 270f
                   if (binding.myTank.translationX > 0)
                    {
                        binding.myTank.translationX -= MOVE_DIST
                    }
                }
        }
    }
}