import React, { useEffect, useState } from "react";
import axios from "axios";
import * as Components from '../css/Components';
import { useNavigate } from "react-router-dom"; // useNavigate 훅 import
import MainPage from './MainPage'; // MainPage 컴포넌트 import

function App() {
        const [signIn, toggle] = useState(true);
        const [password, setPassword] = useState('');
        const [confirmPassword, setConfirmPassword] = useState('');
        const [passwordError, setPasswordError] = useState(false);
        const [userId, setUserId] = useState('');
        const [userPassword, setUserPassword] = useState('');
        const navigate = useNavigate();

        const handleSignInClick = () => {
            navigate('/mainpage'); // '/mainpage' 경로로 리다이렉션
        };

        // 비밀번호 및 비밀번호 확인 변경 핸들러
        const handlePasswordChange = (e) => {
            setPassword(e.target.value);
        };
        const handleConfirmPasswordChange = (e) => {
            setConfirmPassword(e.target.value);
        };

        // SignIn 폼에 사용자 ID와 비밀번호 입력 핸들러 추가
        const handleUserIdChange = (e) => {
            setUserId(e.target.value);
        };

        const handleUserPasswordChange = (e) => {
            setUserPassword(e.target.value);
        };
        //SIGN IN FORM 제출 핸들러
          const signInSubmit = async (e) => {
                e.preventDefault(); // 폼 기본 제출 동작 방지

                try {
                    const response = await axios.post('http://localhost:8080/api/login', {
                        userID: userId,
                        password: userPassword,
                    }, {
                        withCredentials: true  // 쿠키를 요청과 함께 전송하기 위해 설정
                    });

                    const message = response.data;
                    if (message === "Login Successful") { //로그인 성공시
                        alert("Login successful!");
                        navigate('/mainpage');
                    } else {
                        alert(message); // "Invalid credentials" 등 서버에서 정의된 메시지를 표시
                    }
                } catch (error) {
                    console.error("There was an error during login!", error);
                    alert("An error occurred during login.");
                }
            };


        // SIGN UP FORM 제출 핸들러
        const signUpSubmit = async (e) => {
            e.preventDefault(); // 폼 기본 제출 동작 방지

            // 비밀번호 유효성 검사
            if (password !== confirmPassword) {
                setPasswordError(true); // 비밀번호 불일치 에러 표시
                // 여기서 return을 해서 함수를 종료시키면, 비밀번호가 다를 때 추가 동작을 방지할 수 있습니다.
                return;
            } else {
                setPasswordError(false); // 에러 상태 초기화

                // 폼 데이터를 객체로 준비
                const formData = {
                    userID: e.target.userID.value, // 사용자 ID
                    userName: e.target.userName.value, // 사용자 이름
                    sex: e.target.userGender.value, // 성별
                    birthDate: e.target.userBirth.value, // 생년월일
                    phone: e.target.userPhone.value, // 전화번호
                    email: e.target.userEmail.value, // 이메일
                    password: e.target.userPW.value, // 비밀번호
                    role: "user",
                    refreshToken: "",
                };

               try {
                       const response = await axios.post('http://localhost:8080/api/signup', formData);
                       const message = response.data; // 서버로부터 받은 메시지

                       switch(message) {
                           case "This ID is already in use.":
                               alert("Signup failed: This ID is already in use.");
                               break;
                           case "Invalid role.":
                               alert("Signup failed: Invalid role specified.");
                               break;
                           case "Membership registration is complete.":
                               alert("Signup successful!");
                               // 성공 후 추가적인 로직, 예를 들어 페이지 리다이렉션
                               break;

                       }
                   } catch (error) {
                       console.error("There was an error!", error);
                       alert("An error occurred during SIGN UP.");
                   }
            }
        };

    return(
        <Components.Container1><Components.Container>

            <Components.LogoContainer>
                <img src={`/dcloudlogo.png`} alt="dcloudlogo" style={{ maxWidth: '300px', maxHeight: '150px' }} />
            </Components.LogoContainer>


            <Components.SignUpContainer signinIn={signIn}>

                <Components.Form onSubmit={signUpSubmit}>
                    <Components.Title>Create Account</Components.Title>
                    <Components.Input type='text' placeholder='Name' name='userName' required/>

                    <Components.RadioGroup>
                         <Components.RadioButton type="radio" id="male" name="userGender" value="male" defaultChecked />
                         <Components.RadioButtonLabel htmlFor="male">Male</Components.RadioButtonLabel>

                         <Components.RadioButton type="radio" id="female" name="userGender" value="female" />
                         <Components.RadioButtonLabel htmlFor="female">Female</Components.RadioButtonLabel>
                    </Components.RadioGroup>
                    <Components.Input type='date' name="userBirth" placeholder='Date of Birth' required/>
                    <Components.Input type='text' name="userID" placeholder='ID' required/>
                    <Components.Input type='password' name="userPW" id="password" placeholder='Password' value={password} onChange={handlePasswordChange} required minlength="8"/>
                    <Components.Input type='password' id="confirmPassword" placeholder='Confirm Password' value={confirmPassword} onChange={handleConfirmPasswordChange} required minlength="8"/>
                    {passwordError && <p className="passwordError" style={{color: 'red'}}>* Passwords do not match.</p>}
                    <Components.Input type='email' name="userEmail" placeholder='Email' required/>
                    <Components.Input type='tel' name="userPhone" placeholder='Phone (010-0000-0000)' required/>
                    <Components.Button type='submit'>Sign Up</Components.Button>
                </Components.Form>
            </Components.SignUpContainer>


            <Components.SignInContainer signinIn={signIn}>
                        <Components.Form onSubmit={signInSubmit}>
                            <Components.Title>Sign in</Components.Title>
                            <Components.Input type='text' placeholder='ID' value={userId} onChange={handleUserIdChange} required/>
                            <Components.Input type='password' placeholder='Password' value={userPassword} onChange={handleUserPasswordChange} required/>
                            <Components.Anchor href='#'>Forgot your password?</Components.Anchor>
                            <Components.Button type='submit'>Sign In</Components.Button>
                        </Components.Form>
                    </Components.SignInContainer>

            <Components.OverlayContainer signinIn={signIn}>
                <Components.Overlay signinIn={signIn}>
                    <Components.LeftOverlayPanel signinIn={signIn}>
                        <Components.Title>Welcome to DGU AI LAB Container Service</Components.Title>
                        <Components.Paragraph>
                            To keep connected with us please login with your personal info
                        </Components.Paragraph>
                        <Components.GhostButton onClick={() => toggle(true)}>
                            Sign In
                        </Components.GhostButton>
                    </Components.LeftOverlayPanel>

                    <Components.RightOverlayPanel signinIn={signIn}>
                        <Components.Title>Hello, Friend!</Components.Title>
                        <Components.Paragraph>
                            Enter your personal details and start your Analyse with us
                        </Components.Paragraph>
                        <Components.GhostButton onClick={() => toggle(false)}>
                            Sign Up
                        </Components.GhostButton>
                    </Components.RightOverlayPanel>
                </Components.Overlay>
            </Components.OverlayContainer>
        </Components.Container>
</Components.Container1>
    )
}

export default App;
