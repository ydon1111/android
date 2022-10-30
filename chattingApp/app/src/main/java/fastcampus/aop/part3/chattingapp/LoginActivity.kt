package fastcampus.aop.part3.chattingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

import fastcampus.aop.part3.chattingapp.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    lateinit var binding: ActivityLogInBinding

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //회원가입 버튼 이벤트
        binding.signUpBtn.setOnClickListener {
            val intent: Intent = Intent(this@LogInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * 로그인
     */
    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //성공 시 실행
                    val intent: Intent = Intent(this@LogInActivity,
                        MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    //실패 시 실행
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "Error: ${task.exception}")
                }
            }
    }
}