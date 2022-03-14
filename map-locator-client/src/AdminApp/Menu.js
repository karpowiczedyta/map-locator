import React from "react";
import AddBeautifingService from "./AddBeautifingService";
import AddBeautifingServiceType from "./AddBeautifingServiceType";

const Menu = ({ setCategory }) => {
  return (
    <>
      <div className="menuu">
        <button
          class="setCategory"
          onClick={() => {
            setCategory("hairdressing-salons");
          }}
        >
          Salony fryzjerskie
        </button>
        <button
          class="setCategory"
          onClick={() => {
            setCategory("beauty-salons");
          }}
        >
          Salony kosmetyczne
        </button>
        <button
          class="setCategory"
          onClick={() => {
            setCategory("cosmetic-salons");
          }}
        >
          Chirurgia plastyczna
        </button>
        <button
          class="setCategory"
          onClick={() => {
            setCategory("piercing-salons");
          }}
        >
          Piercing
        </button>

        <button
          class="setCategory"
          onClick={() => {
            setCategory("laser-therapy-salons");
          }}
        >
          Laseroterapia
        </button>
        <button
          class="setCategory"
          onClick={() => {
            setCategory("aesthetic-medicine-salons");
          }}
        >
          Medycyna estetyczna
        </button>
        <button
          class="setCategory"
          onClick={() => {
            setCategory("tattoo-studio");
          }}
        >
          Studio tatuazy
        </button>

        <AddBeautifingService />
        <AddBeautifingServiceType />
      </div>
    </>
  );
};

export default Menu;
