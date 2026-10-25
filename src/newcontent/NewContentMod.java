package newcontent;

import arc.Events;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.mod.Mod;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.type.Planet;
import mindustry.type.UnitType;
import mindustry.content.TechTree;
import mindustry.content.TechTree.TechNode;

/**
 * New Content - Java port of the original content + JS mod.
 * Original authors: Rylid Umil & Aegires
 */
public class NewContentMod extends Mod {

    public static final String MOD_NAME = "newunits";

    @Override
    public void init() {
        Events.on(ClientLoadEvent.class, e -> onClientLoad());
    }

    private void onClientLoad() {
        Planet clexon = Vars.content.planet(MOD_NAME + "-clexon");

        Item iron = Vars.content.item(MOD_NAME + "-iron");
        Item scorchonite = Vars.content.item(MOD_NAME + "-scorchonite");
        Item selevite = Vars.content.item(MOD_NAME + "-selevite");
        Liquid poisonWater = Vars.content.liquid(MOD_NAME + "-poison-water");
        Liquid poison = Vars.content.liquid(MOD_NAME + "-poison");

        // Tech tree nodes (ported from original main.js)
        addNode(Liquids.water, iron, 0, null);
        addNode(Liquids.cryofluid, Liquids.water, -1, clexon);
        addNode(Liquids.slag, iron, 0, null);
        addNode(poisonWater, Liquids.water, -1, clexon);
        addNode(poison, Liquids.water, -1, clexon);
        addNode(selevite, poison, 0, clexon);
        addNode(Items.coal, scorchonite, 0, null);
        addNode(Items.scrap, iron, 0, null);
        addNode(Items.sand, Items.scrap, -1, clexon);

        // Weapon name change for crawler
        UnitType crawler = UnitTypes.crawler;
        if (crawler != null && crawler.weapons.size > 0) {
            crawler.weapons.get(0).name = MOD_NAME + "-death-weapon";
            crawler.weapons.get(0).load();
        }

        // Vanilla patches (light radius fixes)
        if (UnitTypes.mace != null && UnitTypes.mace.weapons.size > 0) {
            UnitTypes.mace.weapons.get(0).bullet.lightRadius = 0;
        }
        if (UnitTypes.oxynoe != null && UnitTypes.oxynoe.weapons.size > 0) {
            UnitTypes.oxynoe.weapons.get(0).bullet.lightRadius = 0;
        }
    }

    private void addNode(UnlockableContent contentChild, UnlockableContent contentParent, int index, Planet planet) {
        if (contentChild == null || contentParent == null) return;
        if (contentParent.techNodes == null || contentParent.techNodes.isEmpty()) return;

        if (index < 0) {
            index = contentParent.techNodes.size + index;
        }
        if (index < 0 || index >= contentParent.techNodes.size) index = 0;

        TechNode parentNode = contentParent.techNodes.get(index);
        parentNode.children.add(TechTree.nodeProduce(contentChild, () -> {}));

        if (planet == null) {
            contentChild.shownPlanets.addAll(contentParent.shownPlanets);
            contentChild.databaseTabs.addAll(contentParent.databaseTabs);
        } else {
            contentChild.shownPlanets.add(planet);
            contentChild.databaseTabs.add(planet);
        }
    }
}
