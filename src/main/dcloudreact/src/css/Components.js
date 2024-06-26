import styled from 'styled-components';

 export const Container = styled.div`
 background-color: #fff;
 border-radius: 10px;
 box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
 position: relative;
 overflow: hidden;
 width: 850px;
 max-width: 100%;
 min-height: 950px;

 `;

  export const Container1 = styled.div`
 background: #f6f5f7;
 display: flex;
 justify-content: center;
 align-items: center;
 flex-direction: column;
 font-family: "Montserrat", sans-serif;
 height: 100vh;
 margin: -20px 0 50px;

  `;



 export const SignUpContainer = styled.div`
  position: absolute;
  top: 0;
  height: 100%;
  transition: all 0.6s ease-in-out;
  left: 0;
  width: 50%;
  opacity: 0;
  z-index: 1;
  ${props => props.signinIn !== true ? `
    transform: translateX(100%);
    opacity: 1;
    z-index: 5;
  `
  : null}
 `;


 export const SignInContainer = styled.div`
 position: absolute;
 top: 0;
 height: 100%;
 transition: all 0.6s ease-in-out;
 left: 0;
 width: 50%;
 z-index: 2;
 ${props => (props.signinIn !== true ? `transform: translateX(100%);` : null)}
 `;

 export const Form = styled.form`
 background-color: #ffffff;
 display: flex;
 align-items: center;
 justify-content: center;
 flex-direction: column;
 padding: 0 50px;
 height: 100%;
 text-align: center;
 `;

 export const Title = styled.h1`
 font-weight: bold;
 margin: 0;
 `;

 export const Input = styled.input`
 background-color: #eee;
 border: none;
 padding: 12px 15px;
 margin: 8px 0;
 width: 100%;
 `;


 export const Button = styled.button`
    border-radius: 20px;
    border: 1px solid #FF6600;
    background-color: #FF6600;
    color: #ffffff;
    font-size: 12px;
    font-weight: bold;
    padding: 12px 45px;
    letter-spacing: 1px;
    text-transform: uppercase;
    transition: transform 80ms ease-in;
    &:active{
        transform: scale(0.95);
    }
    &:focus {
        outline: none;
    }
 `;
 export const GhostButton = styled(Button)`
 background-color: transparent;
 border-color: #ffffff;
 `;

 export const Anchor = styled.a`
 color: #333;
 font-size: 14px;
 text-decoration: none;
 margin: 15px 0;
 `;
 export const OverlayContainer = styled.div`
position: absolute;
top: 0;
left: 50%;
width: 50%;
height: 100%;
overflow: hidden;
transition: transform 0.6s ease-in-out;
z-index: 7;
${props =>
  props.signinIn !== true ? `transform: translateX(-100%);` : null}
`;

export const Overlay = styled.div`
background: #FF6600;
background: -webkit-linear-gradient(to right, #FEB309, #FF6600);
background: linear-gradient(to right, #FEB309, #FF6600);
background-repeat: no-repeat;
background-size: cover;
background-position: 0 0;
color: #ffffff;
position: relative;
left: -100%;
height: 100%;
width: 200%;
transform: translateX(0);
transition: transform 0.6s ease-in-out;
${props => (props.signinIn !== true ? `transform: translateX(50%);` : null)}
`;

 export const OverlayPanel = styled.div`
     position: absolute;
     display: flex;
     align-items: center;
     justify-content: center;
     flex-direction: column;
     padding: 0 40px;
     text-align: center;
     top: 0;
     height: 100%;
     width: 50%;
     transform: translateX(0);
     transition: transform 0.6s ease-in-out;
 `;

 export const LeftOverlayPanel = styled(OverlayPanel)`
   transform: translateX(-20%);
   ${props => props.signinIn !== true ? `transform: translateX(0);` : null}
 `;

 export const RightOverlayPanel = styled(OverlayPanel)`
     right: 0;
     transform: translateX(0);
     ${props => props.signinIn !== true ? `transform: translateX(20%);` : null}
 `;

 export const Paragraph = styled.p`
 font-size: 14px;
   font-weight: 100;
   line-height: 20px;
   letter-spacing: 0.5px;
   margin: 20px 0 30px
 `;

export const RadioGroup = styled.div`
  display: flex;
  justify-content: center;
  margin: 20px 0;
`;

export const RadioButton = styled.input`
  display: none;

  &:checked + label {
    background-color: #FF6600;
    color: #ffffff;
  }
`;

export const RadioButtonLabel = styled.label`
  background-color: #eee;
  color: #333;
  display: inline-block;
  padding: 10px 20px;
  font-family: Arial, sans-serif;
  margin: 0 10px;
  border-radius: 20px;
  cursor: pointer;
  border: 2px solid transparent;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #ddd;
  }
`;

export const LogoContainer = styled.div`
  position: absolute;
  top: 5px; // 상단 여백 조정
  left: 50%;
  transform: translateX(-50%); // 중앙 정렬
  z-index: 10; // 다른 요소 위에 오도록 z-index 설정
`;