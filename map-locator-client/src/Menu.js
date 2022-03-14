import { Navbar } from "tombac";
import { NavbarLogo } from "tombac";
import { SelectDistance } from "./SelectDistance";
import React from "react";

const Menu = ({
  setCategory,
  setDistance,
  setOptionToSearch,
  setTreatment,
  setPriceFrom,
  setPriceTo,
  setOpinion,
}) => {
  return (
    <div className="menu">
      <div
        style={{
          backgroundColor: "hsl(0, 0%, 90%)",
          boxShadow: "0 0 16px 0 hsla(0, 0%, 0%, 0.08)",
          overflow: "auto",
        }}
      >
        <Navbar>
          <NavbarLogo
            as="a"
            href="#"
            subtitle="Beautifing services"
            title="MAP"
          />
        </Navbar>
      </div>

      <button
        class="myButton"
        onClick={() => {
          setCategory("hairdressing-salons");
        }}
      >
        Salony fryzjerskie
      </button>
      <button
        class="myButton"
        onClick={() => {
          setCategory("beauty-salons");
        }}
      >
        Salony kosmetyczne
      </button>
      <button
        class="myButton"
        onClick={() => {
          setCategory("cosmetic-salons");
        }}
      >
        Chirurgia plastyczna
      </button>
      <button
        class="myButton"
        onClick={() => {
          setCategory("piercing-salons");
        }}
      >
        Piercing
      </button>

      <button
        class="myButton"
        onClick={() => {
          setCategory("laser-therapy-salons");
        }}
      >
        Laseroterapia
      </button>
      <button
        class="myButton"
        onClick={() => {
          setCategory("aesthetic-medicine-salons");
        }}
      >
        Medycyna estetyczna
      </button>
      <button
        class="myButton"
        onClick={() => {
          setCategory("tattoo-studio");
        }}
      >
        Studio tatuazy
      </button>

      <SelectDistance
        setDistance={setDistance}
        setOptionToSearch={setOptionToSearch}
        setTreatment={setTreatment}
        setPriceFrom={setPriceFrom}
        setPriceTo={setPriceTo}
        setOpinion={setOpinion}
      />
    </div>
  );
};

export default Menu;
