package Snipeware.management;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import Snipeware.Client;
import Snipeware.module.Module;
import Snipeware.module.impl.combat.AimAssist;
import Snipeware.module.impl.combat.AntiBot;
import Snipeware.module.impl.combat.AntiVelocity;
import Snipeware.module.impl.combat.AutoArmor;
import Snipeware.module.impl.combat.AutoClicker;
import Snipeware.module.impl.combat.AutoHead;
import Snipeware.module.impl.combat.AutoPot;
import Snipeware.module.impl.combat.AutoSoup;
import Snipeware.module.impl.combat.BowAimbot;
import Snipeware.module.impl.combat.Criticals;
import Snipeware.module.impl.combat.FastBow;
import Snipeware.module.impl.combat.Hitboxes;
import Snipeware.module.impl.combat.KillAura;
import Snipeware.module.impl.combat.Reach;
import Snipeware.module.impl.combat.Regen;
import Snipeware.module.impl.combat.WTap;
import Snipeware.module.impl.movement.FastLadder;
import Snipeware.module.impl.movement.Flight;
import Snipeware.module.impl.movement.InvMove;
import Snipeware.module.impl.movement.LiquidWalk;
import Snipeware.module.impl.movement.LongJump;
import Snipeware.module.impl.movement.NoSlowDown;
import Snipeware.module.impl.movement.Speed;
import Snipeware.module.impl.movement.Sprint;
import Snipeware.module.impl.movement.Step;
import Snipeware.module.impl.movement.TargetStrafe;
import Snipeware.module.impl.player.AutoGG;
import Snipeware.module.impl.player.AutoPaper;
import Snipeware.module.impl.player.AutoTool;
import Snipeware.module.impl.player.Blink;
import Snipeware.module.impl.player.ChestStealer;
import Snipeware.module.impl.player.FastPlace;
import Snipeware.module.impl.player.FastUse;
import Snipeware.module.impl.player.InvManager;
import Snipeware.module.impl.player.KillSay;
import Snipeware.module.impl.player.NoFall;
import Snipeware.module.impl.player.NoRotate;
import Snipeware.module.impl.player.Notifier;
import Snipeware.module.impl.player.Safewalk;
import Snipeware.module.impl.player.SessionInfo;
import Snipeware.module.impl.player.Zoot;
import Snipeware.module.impl.visuals.BlockAnimation;
import Snipeware.module.impl.visuals.BlockOverlay;
import Snipeware.module.impl.visuals.Chams;
import Snipeware.module.impl.visuals.ChestESP;
import Snipeware.module.impl.visuals.ClickGUI;
import Snipeware.module.impl.visuals.DMGParticles;
import Snipeware.module.impl.visuals.DragonWings;
import Snipeware.module.impl.visuals.ESP2D;
import Snipeware.module.impl.visuals.Fullbright;
import Snipeware.module.impl.visuals.HUD;
import Snipeware.module.impl.visuals.ItemESP;
import Snipeware.module.impl.visuals.ItemPhysic;
import Snipeware.module.impl.visuals.Nametags;
import Snipeware.module.impl.visuals.NoHurtCam;
import Snipeware.module.impl.visuals.OutlineESP;
import Snipeware.module.impl.visuals.PenisESP;
import Snipeware.module.impl.visuals.SkeletonESP;
import Snipeware.module.impl.visuals.TargetHUD;
import Snipeware.module.impl.visuals.Tracers;
import Snipeware.module.impl.world.*;
import Snipeware.util.other.FilesReader;
import Snipeware.value.Value;
import Snipeware.value.impl.BooleanValue;
import Snipeware.value.impl.ColorValue;
import Snipeware.value.impl.EnumValue;
import Snipeware.value.impl.NumberValue;

public class ModuleManager {

	private final Map<Class, Module> modules = new LinkedHashMap<>();
	public KillAura aura;
	public Scaffold scaffold;

	public ChestStealer chestStealer;

	public ModuleManager() {
		modules.put(Scaffold.class, new Scaffold());
		modules.put(Sprint.class, new Sprint());
		modules.put(HUD.class, new HUD());
		modules.put(NoSlowDown.class, new NoSlowDown());
		modules.put(Speed.class, new Speed());
		modules.put(Flight.class, new Flight());
		modules.put(Criticals.class, new Criticals());
		modules.put(AntiVelocity.class, new AntiVelocity());
		modules.put(ClickGUI.class, new ClickGUI());
		modules.put(Disabler.class, new Disabler());
		modules.put(NoFall.class, new NoFall());
		modules.put(KillAura.class, aura = new KillAura());
		modules.put(TargetHUD.class, new TargetHUD());
		modules.put(AntiBot.class, new AntiBot());
		modules.put(BlockAnimation.class, new BlockAnimation());
		modules.put(NoRotate.class, new NoRotate());
		modules.put(DragonWings.class, new DragonWings());
		modules.put(Ambience.class, new Ambience());
		modules.put(Step.class, new Step());
		modules.put(AutoArmor.class, new AutoArmor());
		modules.put(ChestStealer.class, chestStealer = new ChestStealer());
		modules.put(InvManager.class, new InvManager());
		modules.put(AntiFall.class, new AntiFall());
		modules.put(AutoPot.class, new AutoPot());
		modules.put(LongJump.class, new LongJump());
		modules.put(InvMove.class, new InvMove());
		modules.put(Chams.class, new Chams());
		modules.put(AutoTool.class, new AutoTool());
		modules.put(Fullbright.class, new Fullbright());
		modules.put(ChestESP.class, new ChestESP());
		modules.put(Freecam.class, new Freecam());
		modules.put(TargetStrafe.class, new TargetStrafe());
		modules.put(Phase.class, new Phase());
		modules.put(SessionInfo.class, new SessionInfo());
		modules.put(ESP2D.class, new ESP2D());
		modules.put(ItemPhysic.class, new ItemPhysic());
		modules.put(NoHurtCam.class, new NoHurtCam());
		modules.put(SkeletonESP.class, new SkeletonESP());
		modules.put(Nametags.class, new Nametags());
		modules.put(Timer.class, new Timer());
		modules.put(AutoClicker.class, new AutoClicker());
		modules.put(Reach.class, new Reach());
		modules.put(FastUse.class, new FastUse());
		modules.put(AimAssist.class, new AimAssist());
		modules.put(WTap.class, new WTap());
		modules.put(FastBow.class, new FastBow());
		modules.put(FastPlace.class, new FastPlace());
		modules.put(ChatBypass.class, new ChatBypass());
		modules.put(KillSay.class, new KillSay());
		modules.put(Regen.class, new Regen());
		modules.put(AutoBreaker.class, new AutoBreaker());
		modules.put(FastBreak.class, new FastBreak());
		modules.put(AutoGG.class, new AutoGG());
		//modules.put(DMGParticles.class, new DMGParticles());
		modules.put(Blink.class, new Blink());
		modules.put(Tracers.class, new Tracers());
		modules.put(BowAimbot.class, new BowAimbot());
		modules.put(Zoot.class, new Zoot());
		modules.put(Safewalk.class, new Safewalk());
		modules.put(LiquidWalk.class, new LiquidWalk());
		modules.put(AutoSoup.class, new AutoSoup());
		modules.put(FastLadder.class, new FastLadder());
		modules.put(AutoPlay.class, new AutoPlay());
		modules.put(Hitboxes.class, new Hitboxes());
		modules.put(AutoHead.class, new AutoHead());
		modules.put(BlockOverlay.class, new BlockOverlay());
		modules.put(ItemESP.class, new ItemESP());
		modules.put(OutlineESP.class, new OutlineESP());
		modules.put(PenisESP.class, new PenisESP());
		//modules.put(Skyblock.class, new Skyblock());
		//modules.put(Notifier.class, new Notifier());
	}

	public Collection<Module> getModules() {
		return modules.values();
	}

	public Module getModule(final String name) {
		return modules.values().stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst()
				.orElse(null);
	}

	public Module getModule(final Class name) {
		return modules.values().stream().filter(module -> module.getClass().getName().equalsIgnoreCase(name.getName()))
				.findFirst().orElse(null);
	}

	public void saveModules(File data) {
		JsonObject jsonObject = new JsonObject();
		for (Module module : Client.INSTANCE.getModuleManager().getModules()) {
			JsonObject moduleObject = new JsonObject();
			moduleObject.addProperty("name", module.getName());
			moduleObject.addProperty("keybind", module.getKey());
			moduleObject.addProperty("enabled", module.isEnabled());
			module.getValues()
					.forEach(value -> moduleObject.addProperty(value.getLabel(), String.valueOf(value.getValue())));
			jsonObject.add(module.getName(), moduleObject);
		}
		try {
			FileWriter fileWriter = new FileWriter(data);
			fileWriter.write(new GsonBuilder().create().toJson(jsonObject));
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadModules(File data) {
		if (!data.exists()) {
			return;
		}
		Client.INSTANCE.getModuleManager().getModules().forEach(module -> module.setEnabled(false));
		String content = FilesReader.readFile(data);

		JsonObject configurationObject = new GsonBuilder().create().fromJson(content, JsonObject.class);

		for (Map.Entry<String, JsonElement> entry : configurationObject.entrySet()) {
			if (entry.getValue() instanceof JsonObject) {
				JsonObject moduleObject = (JsonObject) entry.getValue();

				for (Module module : Client.INSTANCE.getModuleManager().getModules()) {
					if (module.getName().equalsIgnoreCase(moduleObject.get("name").getAsString())) {
						module.setKeyBind(moduleObject.get("keybind").getAsInt());
						if (moduleObject.get("enabled").getAsBoolean()) {
							module.toggle();
						} else {
							module.setEnabled(false);
						}

						for (Value value : module.getValues()) {
							if (moduleObject.get(value.getLabel()) != null) {
								if (value instanceof NumberValue) {
									if (value.getValue() instanceof Double) {
										value.setValue(moduleObject.get(value.getLabel()).getAsDouble());
									}
									if (value.getValue() instanceof Integer) {
										value.setValue(moduleObject.get(value.getLabel()).getAsInt());
									}
									if (value.getValue() instanceof Float) {
										value.setValue(moduleObject.get(value.getLabel()).getAsFloat());
									}
									if (value.getValue() instanceof Short) {
										value.setValue(moduleObject.get(value.getLabel()).getAsShort());
									}
									if (value.getValue() instanceof Byte) {
										value.setValue(moduleObject.get(value.getLabel()).getAsByte());
									}
									if (value.getValue() instanceof Long) {
										value.setValue(moduleObject.get(value.getLabel()).getAsLong());
									}
								}
								if (value instanceof BooleanValue) {
									value.setValue(moduleObject.get(value.getLabel()).getAsBoolean());
								}
								if (value instanceof ColorValue) {
									value.setValue(moduleObject.get(value.getLabel()).getAsInt());
								}
								if (value instanceof EnumValue) {
									for (int i = 0; i < ((EnumValue) value).getConstants().length; i++) {
										if (((EnumValue) value).getConstants()[i].name()
												.equalsIgnoreCase(moduleObject.get(value.getLabel()).getAsString())) {
											value.setValue(((EnumValue) value).getConstants()[i]);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
