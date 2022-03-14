import { Navbar } from "tombac";
import {
  NavbarLogo,
  NavbarMenu,
  NavbarMenuItem,
  NavbarMenuLink,
  NavbarCollapse,
} from "tombac";
import ManageSalons from "./ManageSalons";
import ManageUser from "./ManageUser";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

const Menu = () => {
  return (
    <>
      <Router>
        <div
          style={{
            backgroundColor: "hsl(0, 0%, 90%)",
            boxShadow: "0 0 16px 0 hsla(0, 0%, 0%, 0.08)",
            overflow: "auto",
          }}
        >
          <Navbar>
            <NavbarLogo as="a" href="#" subtitle="O/D Analysis" title="Move" />
            <NavbarCollapse minWidth={500}>
              <NavbarMenu>
                <NavbarMenuItem>
                  <NavbarMenuLink className="active" href="#">
                    <Link class="manageSalon" to="/manageSalons">
                      Manage Salons
                    </Link>
                  </NavbarMenuLink>
                </NavbarMenuItem>
                <NavbarMenuItem>
                  <button
                    class="logout-admin"
                    onClick={() => {
                      window.location = `http://test.move.tomtom.com/logout?redirect=${window.location}`;
                    }}
                  >
                    Logout
                  </button>
                </NavbarMenuItem>
              </NavbarMenu>
            </NavbarCollapse>
          </Navbar>
        </div>
        <Switch>
          <Route path="/manageSalons">
            <ManageSalons />
          </Route>
          <Route path="/manageUser">
            <ManageUser />
          </Route>
        </Switch>
      </Router>
    </>
  );
};

export default Menu;
