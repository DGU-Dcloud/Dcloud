import React, { useEffect, useState } from "react";
import axios from "axios";

import * as Components from './Components';

function App() {
    const [signIn, toggle] = React.useState(true);
    return(
        <Components.Container>
            <Components.SignUpContainer signinIn={signIn}>



                <Components.Form>
                    <Components.Title>Create Account</Components.Title>
                    <Components.Input type='text' placeholder='Name' />

                    <Components.RadioGroup>
                         <Components.RadioButton type="radio" id="male" name="gender" value="male" defaultChecked />
                         <Components.RadioButtonLabel htmlFor="male">Male</Components.RadioButtonLabel>

                         <Components.RadioButton type="radio" id="female" name="gender" value="female" />
                         <Components.RadioButtonLabel htmlFor="female">Female</Components.RadioButtonLabel>
                    </Components.RadioGroup>

                    <Components.Input type='date' placeholder='Date of Birth' />
                    <Components.Input type='text' placeholder='ID' />
                    <Components.Input type='password' placeholder='Password' />
                    <Components.Input type='password' placeholder='Confirm Password' />
                    <Components.Input type='email' placeholder='Email' />
                    <Components.Input type='tel' placeholder='Phone Number' />
                    <Components.Button>Sign Up</Components.Button>
                </Components.Form>
            </Components.SignUpContainer>

            <Components.SignInContainer signinIn={signIn}>
                <Components.Form>
                    <Components.Title>Sign in</Components.Title>
                    <Components.Input type='text' placeholder='ID' />
                    <Components.Input type='password' placeholder='Password' />
                    <Components.Anchor href='#'>Forgot your password?</Components.Anchor>
                    <Components.Button>Sign In</Components.Button>
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
