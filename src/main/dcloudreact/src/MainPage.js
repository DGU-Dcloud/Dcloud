import React, { useEffect, useState } from "react";
import axios from "axios";

const MainPage = () => {
  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">
      <div className="bg-white shadow-lg rounded-lg">
        <div className="flex flex-col lg:flex-row">
          <div className="bg-red-600 text-white p-8 lg:w-96">
            <h2 className="text-xl mb-4">서비스 점검 안내</h2>
            <p className="mb-2">점검기간: 오전 7시 ~ 오전 12시</p>
            <p className="mb-2">(정상 서비스 재개 시간은 변동될 수 있습니다.)</p>
            {/* Additional details and links */}
            {/* ... */}
            <p className="mt-6 text-xs">© 2021 DONGGUK UNIVERSITY. ALL RIGHTS RESERVED.</p>
          </div>
          <div className="p-8">
            <div className="mb-4">
              <div className="flex justify-between items-center mb-2">
                <h1 className="text-xl font-bold">DONGGUK UNIVERSITY</h1>
                <div className="flex">
                  <button className="bg-gray-200 px-4 py-2 rounded-l focus:outline-none">KOR</button>
                  <button className="bg-gray-300 px-4 py-2 rounded-r focus:outline-none">ENG</button>
                </div>
              </div>
              <input type="text" placeholder="2018112032" className="border-b-2 border-gray-300 w-full mb-4 px-2 py-1 focus:outline-none" />
              <input type="password" placeholder="********" className="border-b-2 border-gray-300 w-full mb-4 px-2 py-1 focus:outline-none" />
              <button className="w-full bg-orange-400 text-white px-4 py-2 rounded hover:bg-orange-500 focus:outline-none">LOGIN</button>
            </div>
            {/* Footer with address and contact */}
            {/* ... */}
          </div>
        </div>
      </div>
    </div>
  );
};

export default MainPage;
