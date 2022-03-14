import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import AdminApp from "./AdminApp/AdminApp";
import { LegolandApp } from "legoland-sdk/dist/experimental";
import { RequireAuth, UserRole } from "legoland-sdk/dist/experimental";
import { TombacApp } from "tombac";

ReactDOM.render(
  <React.StrictMode>
    <TombacApp>
      <LegolandApp
        legolandApiUrl="https://test.api.move.tomtom.com"
        authApiUrl="https://test.auth.move.tomtom.com"
        legolandUiUrl="https://test.move.tomtom.com"
      >
        <RequireAuth autoRedirect>
          <RequireAuth role={UserRole.User}>
            <App />
          </RequireAuth>
          <RequireAuth role={UserRole.Admin}>
            <AdminApp />
          </RequireAuth>
        </RequireAuth>
      </LegolandApp>
    </TombacApp>
  </React.StrictMode>,
  document.getElementById("root")
);
