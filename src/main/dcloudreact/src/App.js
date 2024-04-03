import React, { useEffect, useState } from "react";
import axios from "axios";
import * as Components from './Components';
import { useNavigate } from "react-router-dom"; // useNavigate 훅 import
import MainPage from './MainPage'; // MainPage 컴포넌트 import

function App() {
    const [signIn, toggle] = useState(true);
        const [password, setPassword] = useState('');
        const [confirmPassword, setConfirmPassword] = useState('');
        const [passwordError, setPasswordError] = useState(false); // 비밀번호 에러 상태
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

        // SIGN UP FORM 제출 핸들러
        const handleSubmit = async (e) => {
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
                    password, // 비밀번호
                    confirmPassword, // 비밀번호 확인
                    // 다른 필요한 폼 데이터를 여기에 추가하세요.
                };

                try {
                    const response = await axios.post('http://localhost/api/signup', formData);

                    if (response.data.success) {
                        alert("Signup successful!"); // 성공 알림
                        // 추가적인 성공 처리 로직 (예: 로그인 페이지로 리다이렉션)
                    } else {
                        alert("Signup failed: " + response.data.message); // 실패 알림 및 이유
                    }
                } catch (error) {
                    console.error("There was an error!", error);
                    alert("An error occurred during SIGN UP."); // 에러 알림
                }


            }
        };

    return(
        <Components.Container>

            <Components.LogoContainer>
                <img src={`/dcloudlogo.png`} alt="dcloudlogo" style={{ maxWidth: '300px', maxHeight: '150px' }} />
            </Components.LogoContainer>


            <Components.SignUpContainer signinIn={signIn}>

                <Components.Form onSubmit={handleSubmit}>
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
                <Components.Form>
                    <Components.Title>Sign in</Components.Title>
                    <Components.Input type='text' placeholder='ID' />
                    <Components.Input type='password' placeholder='Password' />
                    <Components.Anchor href='#'>Forgot your password?</Components.Anchor>
                    <Components.Button onClick={handleSignInClick}>Sign In</Components.Button>
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

    )
}

export default App;
