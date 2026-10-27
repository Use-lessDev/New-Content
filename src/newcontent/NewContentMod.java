package newcontent;

import arc.Events;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.game.EventType.ContentInitEvent;
import mindustry.mod.Mod;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.type.Planet;
import mindustry.type.UnitType;
import mindustry.content.TechTree.TechNode;

/**
 * New Content - Java port of the original mod's runtime changes.
 *
 * The HJSON content remains data-driven under /content. This class handles
 * the changes that require Java after all base and mod content is initialized.
 * Original authors: Rylid Umil & Aegires.
 * Ported by Use-lessDev.
 */
public class NewContentMod extends Mod {

    /** Must match the "name" field in mod.hjson. */
    public static final String MOD_NAME = "newunits";

    @Override
    public void init() {
        // ContentInitEvent runs after base and mod content are initialized.
        // Unlike ClientLoadEvent, it is not tied to the client UI lifecycle.
        Events.on(ContentInitEvent.class, event -> applyRuntimeChanges());
        // Sprite regions are safe to reload once the client atlas/UI has loaded.
        Events.on(ClientLoadEvent.class, event -> loadCrawlerWeaponRegion());
    }

    private void applyRuntimeChanges() {
        Planet clexon = Vars.content.planet(MOD_NAME + "-clexon");

        Item iron = Vars.content.item(MOD_NAME + "-iron");
        Item scorchonite = Vars.content.item(MOD_NAME + "-scorchonite");
        Item selevite = Vars.content.item(MOD_NAME + "-selevite");
        Liquid poisonWater = Vars.content.liquid(MOD_NAME + "-poison-water");
        Liquid poison = Vars.content.liquid(MOD_NAME + "-poison");

        // Preserve the extra research-tree branches from the original script.
        addNode(Liquids.water, iron, 0, null);
        addNode(Liquids.cryofluid, Liquids.water, -1, clexon);
        addNode(Liquids.slag, iron, 0, null);
        addNode(poisonWater, Liquids.water, -1, clexon);
        addNode(poison, Liquids.water, -1, clexon);
        addNode(selevite, poison, 0, clexon);
        addNode(Items.coal, scorchonite, 0, null);
        addNode(Items.scrap, iron, 0, null);
        addNode(Items.sand, Items.scrap, -1, clexon);

        // Keep these vanilla-unit changes on both client and headless loads.
        UnitType crawler = UnitTypes.crawler;
        if (crawler != null && crawler.weapons.size > 0) {
            // Set the name before the engine's content-loading pass uses it.
            crawler.weapons.get(0).name = MOD_NAME + "-death-weapon";
        }

        if (UnitTypes.mace != null && UnitTypes.mace.weapons.size > 0) {
            UnitTypes.mace.weapons.get(0).bullet.lightRadius = 0f;
        }
        if (UnitTypes.oxynoe != null && UnitTypes.oxynoe.weapons.size > 0) {
            UnitTypes.oxynoe.weapons.get(0).bullet.lightRadius = 0f;
        }
    }

    private void loadCrawlerWeaponRegion() {
        UnitType crawler = UnitTypes.crawler;
        if (crawler != null && crawler.weapons.size > 0) {
            crawler.weapons.get(0).load();
        }
    }

    private void addNode(UnlockableContent child, UnlockableContent parent, int index, Planet planet) {
        if (child == null || parent == null) return;
        if (parent.techNodes == null || parent.techNodes.isEmpty()) return;

        int resolvedIndex = index;
        if (resolvedIndex < 0) {
            resolvedIndex = parent.techNodes.size + resolvedIndex;
        }
        if (resolvedIndex < 0 || resolvedIndex >= parent.techNodes.size) {
            resolvedIndex = 0;
        }

        TechNode parentNode = parent.techNodes.get(resolvedIndex);
        if (parentNode == null) return;

        parentNode.children.add(TechTree.nodeProduce(child, () -> {}));

        if (planet == null) {
            child.shownPlanets.addAll(parent.shownPlanets);
            child.databaseTabs.addAll(parent.databaseTabs);
        } else {
            child.shownPlanets.add(planet);
            child.databaseTabs.add(planet);
        }
    }
}
