package ru.kpfu.itis.hw_android_2022

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import ru.kpfu.itis.hw_android_2022.databinding.MainFragmentBinding
import java.util.concurrent.TimeUnit

class MainFragment : Fragment(R.layout.main_fragment), Observer {

    //binding
    private var _binding: MainFragmentBinding? = null
    private val binding by lazy { _binding!! }

    //notification variables
    private var headerText: String? = null
    private var messageBodyText: String? = null
    private var messageBodyTextDetailed: String? = null
    private var delayTime: Int = 0

    private var isCheckBoxChecked = false

    //edittext booleans
    private var isHeaderEmpty = true
    private var isMessageBodyEmpty = true
    private var isTimeFieldEmpty = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonListeners()
        initCheckBoxListener()
        initEditTextListener(binding.ilNotificationHeader.editText)
        initEditTextListener(binding.ilNotificationBody.editText)
        initEditTextListener(binding.ilNotificationTime.editText)

    }

    override fun onStart() {
        activity?.registerReceiver(FlyModeReceiver(), IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        AirModeInterfaceImpl.isOn = !checkAirMode(context)
        AirModeInterfaceImpl.registerObserver(this)
        AirModeInterfaceImpl.notifyObserver()
        super.onStart()
    }


    private fun initButtonListeners() {
        with(binding) {
            btnShowAlarm.setOnClickListener {
                headerText = etNotificationHeader.text.toString()
                messageBodyText = etNotificationBody.text.toString()
                // если пользователь чекнул checkbox, ввёл текст, а потом убрал галочку, доп.текст не должен отправляться
                // но при этом удаляться тоже не должен. Логично???
                messageBodyTextDetailed = if (isCheckBoxChecked) {
                    etNotificationBodyDetailed.text.toString()
                } else {
                    null
                }

                // в xml целые числа, поэтому преобразование никогда не выбросит исключение
                delayTime = Integer.valueOf(etNotificationTime.text.toString())
                AlarmManagerHelper.setAlarm(delayTime, activity, getPendingIntent())
            }
            btnDisableAlarm.setOnClickListener {
                //разница между заданным временем и текущим
                //если разница меньше нуля, то уведомление уже было воспроизведено
                if (AlarmManagerHelper.getTimeRemaining() > 0L) {
                    showToast(root.context, getString(R.string.success_message, (AlarmManagerHelper.getTimeRemaining() / 1000).toString()))
                    AlarmManagerHelper.cancelAlarm(activity, getPendingIntent())
                    delayTime = 0
                } else {
                    showToast(root.context,  getString(R.string.failure_message))
                }
            }
            btnShowDeviceRebootTime.setOnClickListener {
                val timeInMillis = SystemClock.uptimeMillis()
                val time = String.format(
                    getString(
                        R.string.milliseconds_to_minutes_and_seconds,
                        TimeUnit.MILLISECONDS.toMinutes(timeInMillis),
                        TimeUnit.MILLISECONDS.toSeconds(timeInMillis) -
                                TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(timeInMillis)
                                )
                    )
                )
                showToast(root.context, time)
            }
        }
    }

    private fun initCheckBoxListener() {
        with(binding) {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                ilNotificationBodyDetailed.isEnabled = isChecked
                isCheckBoxChecked = isChecked
            }
        }
    }

    private fun initEditTextListener(editText: EditText?) {
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                when (editText.id) {
                    R.id.et_notification_header -> isHeaderEmpty = s?.length == 0
                    R.id.et_notification_body -> isMessageBodyEmpty = s?.length == 0
                    R.id.et_notification_time -> isTimeFieldEmpty = s?.length == 0
                }
            }
            override fun afterTextChanged(s: Editable?) {
                checkIfNotEmpty()
            }
        })

    }

    private fun checkIfNotEmpty() {
        binding.btnShowAlarm.isEnabled = !isHeaderEmpty && !isMessageBodyEmpty && !isTimeFieldEmpty
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(
            FlyModeReceiver()
        )
    }


    private fun getPendingIntent(): PendingIntent {
        val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        return Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.BODY_TEXT, messageBodyText)
            putExtra(AlarmReceiver.HEADER_TEXT, headerText)
            putExtra(AlarmReceiver.BODY_TEXT_DETAILED, messageBodyTextDetailed)
        }.let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, pendingIntentFlag)
        }
    }

    private fun turnOn() {
        binding.root.forEach {
            when(it.id) {
                R.id.il_notification_body_detailed -> initCheckBoxListener()
                R.id.btn_show_alarm -> checkIfNotEmpty()
                else -> it.isEnabled = true
            }
        }
    }

    private fun turnOff() {
        binding.root.forEach {
            it.isEnabled = false
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
        fun createInstance(bundle: Bundle?) = MainFragment().apply {
            arguments = bundle
        }
    }

    override fun update(isOn: Boolean) {
        if(isOn) turnOff() else turnOn()
    }

    private fun checkAirMode(context: Context?) = Settings.Global.getInt(context?.contentResolver,
        Settings.Global.AIRPLANE_MODE_ON, 0) == 0
}
