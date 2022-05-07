package com.company;


import java.util.Random;

public class Main {
    public static int bossHealth = 800;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 500, 300};
    public static int[] heroesDamage = {25, 20, 15, 10, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Golem", "Healing"};
    public static int round_number = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        round_number++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        medicAct();
        printStatistics();
    }

    private static void medicAct() {
        if (heroesHealth[heroesHealth.length - 1] > 0) {
            Random gamehealth = new Random();
            int indexOfSelectedHero = gamehealth.nextInt(heroesHealth.length - 2);
            int coeff = gamehealth.nextInt(4) + 1;
            int healthOfSelectedHero = heroesHealth[indexOfSelectedHero];

            if (heroesHealth[indexOfSelectedHero] < 100) {
                if (heroesHealth[indexOfSelectedHero] > 0) {
                    heroesHealth[indexOfSelectedHero] = coeff * healthOfSelectedHero;
                    System.out.println();
                    System.out.println("Medic vilichel geroya " + heroesAttackType[indexOfSelectedHero]
                            + " ispolzuya coeffizent " + coeff + " i vostanovil zdorove geroya na "
                            + coeff * healthOfSelectedHero + " ediniz");
                } else {
                    System.out.println(heroesAttackType[indexOfSelectedHero] + " geroyi umer i medic ne smog emu pomoch" +
                            "hotya ispolzoval " + coeff + " coeffizient");
                }
            } else {
                System.out.println("Medic ispolzya coeffizient, "+coeff+" Medic popitalsya vilechet geroya"
                        + heroesAttackType[indexOfSelectedHero] + " no on bil zdorov");
            }
            System.out.println();
        }
    }

        public static void chooseBossDefence () {
            Random random = new Random();
            int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
            bossDefence = heroesAttackType[randomIndex];
            System.out.println("Boss chose: " + bossDefence);

        }

        public static void printStatistics () {
            System.out.println(round_number + " ROUND _____________");
            System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
            for (int i = 0; i < heroesHealth.length; i++) {
                System.out.println(heroesAttackType[i] + " health: "
                        + heroesHealth[i] + " (" + heroesDamage[i] + ")");
            }
        }

        public static boolean isGameFinished () {
            if (bossHealth <= 0) {
                System.out.println("Heroes won!!!");
                return true;
            }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
            boolean allHeroesDead = true;
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0) {
                    allHeroesDead = false;
                    break;
                }
            }
            if (allHeroesDead) {
                System.out.println("Boss won!!!");
            }
            return allHeroesDead;
        }

        public static void bossHits () {
            bossDamage = golemProtectOtherHeroes();
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] <= 0) {
                    continue;
                }
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
            bossDamage = 50;
        }

    private static int golemProtectOtherHeroes() {
        int fullBossDamage;
        if (heroesHealth[heroesHealth.length - 2] > 0) {
            fullBossDamage = bossDamage - 10;
            int aliveHeroes = -1;
            for (int i : heroesHealth) {
                if (i>0){
                    aliveHeroes++;
                }
            }
            if (heroesHealth[heroesHealth.length-2]- aliveHeroes * 10 > 0) {
                heroesHealth[heroesHealth.length-2] = heroesHealth[heroesHealth.length-2]- (aliveHeroes + 1) * 10;
                System.out.println((aliveHeroes) * 10 +" urona Golem prinyal na seba v dobavok k svoemu");
            } else {
                fullBossDamage=50;
            }
        }else {
            fullBossDamage = 50;
        }
        return fullBossDamage;
    }

    public static void heroesHit () {
            for (int i = 0; i < heroesDamage.length; i++) {
                if (bossHealth <= 0 || heroesHealth[i] <= 0) {
                    continue;
                }
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    System.out.println("Hero's coeff "+ coeff);
                    int criticalDamage = heroesDamage[i] * coeff;
                    if (bossHealth - criticalDamage < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - criticalDamage;
                    }
                    System.out.println("Critical damage: " + criticalDamage);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

