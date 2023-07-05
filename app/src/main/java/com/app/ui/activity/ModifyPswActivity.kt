package com.app.ui.activity
import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.app.R
import com.app.util.showToast

class ModifyPswActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_psw)
       val prefs = getPreferences(Context.MODE_PRIVATE)
       val editor = prefs.edit()
        val accountEdit = findViewById<EditText>(R.id.edt_UserName)
        val passwordEdit = findViewById<EditText>(R.id.edt_NewPassWord)
        val btn_Confirm = findViewById<Button>(R.id.btn_Confirm)

        btn_Confirm.setOnClickListener {
            val account = accountEdit.text.toString()
            val password = passwordEdit.text.toString()

            //必须使用含有字母的账号
            val check:Boolean=true
            for(i in 0..account.length-1){
                if(account[i]-'0'>9||account[i]-'0'<0)//不是数字
                    break
            }
            if(!check) "请输入含有字母的修改账号".showToast()


            editor.putString("account", account)
            editor.putString("password", password)
            editor.apply()
            val intent = Intent(this@ModifyPswActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}