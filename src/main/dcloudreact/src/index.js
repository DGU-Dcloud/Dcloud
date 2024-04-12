import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './screen/Sign';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import MainPage from './screen/MainPage';
import ContainerRequestForm from './screen/ContainerRequestForm';
import ContainerLookup from './screen/ContainerLookup';
import ReportErrors from './screen/ReportErrors';
import MyPage from './screen/MyPage';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />} /> {/* 기본 경로 설정 */}
        <Route path="/mainpage" element={<MainPage />} /> {/* MainPage 컴포넌트에 대한 경로 설정 */}
        <Route path="/containerrequestform" element={<ContainerRequestForm />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/containerlookup" element={<ContainerLookup />} />
        <Route path="/reporterrors" element={<ReportErrors />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
